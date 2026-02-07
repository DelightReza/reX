package com.exteragram.messenger.utils.text;

import android.content.res.Resources;
import android.text.TextUtils;
import android.text.style.URLSpan;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.translators.BaseTranslator;
import com.exteragram.messenger.translators.BaseTranslator$$ExternalSyntheticLambda0;
import com.exteragram.messenger.translators.DeepLTranslator;
import com.exteragram.messenger.translators.GoogleTranslator;
import com.exteragram.messenger.translators.YandexTranslator;
import com.exteragram.messenger.utils.ChatUtils;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LanguageDetector;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.TranslateController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ChatActivity;
import org.telegram.p023ui.Components.TranslateAlert2;
import org.telegram.p023ui.RestrictedLanguagesSelectActivity;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import p017j$.util.Collection;
import p017j$.util.Objects;
import p017j$.util.function.Function$CC;
import p017j$.util.function.IntPredicate$CC;
import p017j$.util.function.Predicate$CC;
import p017j$.util.stream.IntStream;

/* loaded from: classes.dex */
public abstract class TranslatorUtils {
    private static final String[] DEVICE_MODELS = {"Galaxy S6", "Galaxy S7", "Galaxy S8", "Galaxy S9", "Galaxy S10", "Galaxy S21", "Pixel 3", "Pixel 4", "Pixel 5", "OnePlus 6", "OnePlus 7", "OnePlus 8", "OnePlus 9", "Xperia XZ", "Xperia XZ2", "Xperia XZ3", "Xperia 1", "Xperia 5", "Xperia 10", "Xperia L4"};
    private static final String[] CHROME_VERSIONS = {"111.0.5563.57", "94.0.4606.81", "80.0.3987.119", "69.0.3497.100", "92.0.4515.159", "71.0.3578.99"};
    private static final List languages = new ArrayList(TranslateController.getLanguages());

    /* renamed from: $r8$lambda$7PDwCcxK9HE-tSz9oi26pu3desk, reason: not valid java name */
    public static /* synthetic */ void m2096$r8$lambda$7PDwCcxK9HEtSz9oi26pu3desk(Exception exc) {
    }

    public static String formatUserAgent() {
        String strValueOf = String.valueOf(Utilities.random.nextInt(7) + 6);
        String[] strArr = DEVICE_MODELS;
        String str = strArr[Utilities.random.nextInt(strArr.length)];
        String[] strArr2 = CHROME_VERSIONS;
        return String.format("Mozilla/5.0 (Linux; Android %s; %s) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/%s Mobile Safari/537.36", strValueOf, str, strArr2[Utilities.random.nextInt(strArr2.length)]);
    }

    public static String getLanguageTitleSystem(final String str) {
        return (String) Collection.EL.stream(languages).filter(new Predicate() { // from class: com.exteragram.messenger.utils.text.TranslatorUtils$$ExternalSyntheticLambda0
            public /* synthetic */ Predicate and(Predicate predicate) {
                return Predicate$CC.$default$and(this, predicate);
            }

            public /* synthetic */ Predicate negate() {
                return Predicate$CC.$default$negate(this);
            }

            /* renamed from: or */
            public /* synthetic */ Predicate m242or(Predicate predicate) {
                return Predicate$CC.$default$or(this, predicate);
            }

            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((TranslateController.Language) obj).code.equals(str);
            }
        }).findFirst().map(new Function() { // from class: com.exteragram.messenger.utils.text.TranslatorUtils$$ExternalSyntheticLambda1
            public /* synthetic */ Function andThen(Function function) {
                return Function$CC.$default$andThen(this, function);
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((TranslateController.Language) obj).displayName;
            }

            public /* synthetic */ Function compose(Function function) {
                return Function$CC.$default$compose(this, function);
            }
        }).orElse(LocaleController.getString(C2369R.string.VibrationDisabled));
    }

    public static String getLanguageDisplayName(final String str) {
        return (String) Collection.EL.stream(languages).filter(new Predicate() { // from class: com.exteragram.messenger.utils.text.TranslatorUtils$$ExternalSyntheticLambda2
            public /* synthetic */ Predicate and(Predicate predicate) {
                return Predicate$CC.$default$and(this, predicate);
            }

            public /* synthetic */ Predicate negate() {
                return Predicate$CC.$default$negate(this);
            }

            /* renamed from: or */
            public /* synthetic */ Predicate m243or(Predicate predicate) {
                return Predicate$CC.$default$or(this, predicate);
            }

            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((TranslateController.Language) obj).code.equals(str);
            }
        }).findFirst().map(new Function() { // from class: com.exteragram.messenger.utils.text.TranslatorUtils$$ExternalSyntheticLambda3
            public /* synthetic */ Function andThen(Function function) {
                return Function$CC.$default$andThen(this, function);
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((TranslateController.Language) obj).ownDisplayName;
            }

            public /* synthetic */ Function compose(Function function) {
                return Function$CC.$default$compose(this, function);
            }
        }).orElse(null);
    }

    public static String getLangCodeByIndex(int i) {
        if (i < 0) {
            return null;
        }
        List list = languages;
        if (i < list.size()) {
            return ((TranslateController.Language) list.get(i)).code;
        }
        return null;
    }

    public static int getLanguageIndexByIso(final String str) {
        return IntStream.CC.range(0, languages.size()).filter(new IntPredicate() { // from class: com.exteragram.messenger.utils.text.TranslatorUtils$$ExternalSyntheticLambda6
            public /* synthetic */ IntPredicate and(IntPredicate intPredicate) {
                return IntPredicate$CC.$default$and(this, intPredicate);
            }

            public /* synthetic */ IntPredicate negate() {
                return IntPredicate$CC.$default$negate(this);
            }

            /* renamed from: or */
            public /* synthetic */ IntPredicate m244or(IntPredicate intPredicate) {
                return IntPredicate$CC.$default$or(this, intPredicate);
            }

            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return ((TranslateController.Language) TranslatorUtils.languages.get(i)).code.equals(str);
            }
        }).findFirst().orElse(-1);
    }

    public static String[] getLanguageTitles() {
        return (String[]) Collection.EL.stream(languages).map(new Function() { // from class: com.exteragram.messenger.utils.text.TranslatorUtils$$ExternalSyntheticLambda4
            public /* synthetic */ Function andThen(Function function) {
                return Function$CC.$default$andThen(this, function);
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return TranslatorUtils.$r8$lambda$cBV1pqpQCJHx44LQxJJHDOKjhwM((TranslateController.Language) obj);
            }

            public /* synthetic */ Function compose(Function function) {
                return Function$CC.$default$compose(this, function);
            }
        }).toArray(new IntFunction() { // from class: com.exteragram.messenger.utils.text.TranslatorUtils$$ExternalSyntheticLambda5
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return TranslatorUtils.$r8$lambda$N2FPKybNl9xS6g24AH_6_xyZliY(i);
            }
        });
    }

    public static /* synthetic */ String $r8$lambda$cBV1pqpQCJHx44LQxJJHDOKjhwM(TranslateController.Language language) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(language.displayName);
        if (language.ownDisplayName == null) {
            str = "";
        } else {
            str = " – " + language.ownDisplayName;
        }
        sb.append(str);
        return sb.toString();
    }

    public static /* synthetic */ String[] $r8$lambda$N2FPKybNl9xS6g24AH_6_xyZliY(int i) {
        return new String[i];
    }

    public static void translateWithAlert(final MessageObject messageObject, MessageObject.GroupedMessages groupedMessages, final TLRPC.InputPeer inputPeer, final int i, final BaseFragment baseFragment) {
        if (messageObject == null) {
            return;
        }
        final ChatActivity chatActivity = (ChatActivity) baseFragment;
        final Utilities.CallbackReturn callbackReturn = new Utilities.CallbackReturn() { // from class: com.exteragram.messenger.utils.text.TranslatorUtils$$ExternalSyntheticLambda10
            @Override // org.telegram.messenger.Utilities.CallbackReturn
            public final Object run(Object obj) {
                return TranslatorUtils.$r8$lambda$ApJaULNEghSJIOS0hQl4OnarYfE(chatActivity, messageObject, (URLSpan) obj);
            }
        };
        TLRPC.Message message = messageObject.messageOwner;
        final ArrayList arrayList = message != null ? message.entities : null;
        final CharSequence messageText = ChatUtils.getInstance().getMessageText(messageObject, groupedMessages);
        LanguageDetector.detectLanguage(messageText == null ? "" : messageText.toString(), new LanguageDetector.StringCallback() { // from class: com.exteragram.messenger.utils.text.TranslatorUtils$$ExternalSyntheticLambda11
            @Override // org.telegram.messenger.LanguageDetector.StringCallback
            public final void run(String str) {
                TranslatorUtils.$r8$lambda$_NRCzNGAao53KSH5PEoZZwOiisg(baseFragment, inputPeer, i, messageText, arrayList, callbackReturn, chatActivity, str);
            }
        }, new LanguageDetector.ExceptionCallback() { // from class: com.exteragram.messenger.utils.text.TranslatorUtils$$ExternalSyntheticLambda12
            @Override // org.telegram.messenger.LanguageDetector.ExceptionCallback
            public final void run(Exception exc) {
                TranslatorUtils.m2096$r8$lambda$7PDwCcxK9HEtSz9oi26pu3desk(exc);
            }
        });
    }

    public static /* synthetic */ Boolean $r8$lambda$ApJaULNEghSJIOS0hQl4OnarYfE(ChatActivity chatActivity, MessageObject messageObject, URLSpan uRLSpan) throws Resources.NotFoundException, NumberFormatException, UnsupportedEncodingException {
        chatActivity.didPressMessageUrl(uRLSpan, false, messageObject, null);
        return Boolean.TRUE;
    }

    public static /* synthetic */ void $r8$lambda$_NRCzNGAao53KSH5PEoZZwOiisg(BaseFragment baseFragment, TLRPC.InputPeer inputPeer, int i, CharSequence charSequence, ArrayList arrayList, Utilities.CallbackReturn callbackReturn, final ChatActivity chatActivity, String str) {
        String language = LocaleController.getInstance().getCurrentLocale().getLanguage();
        if (str != null) {
            if ((!str.equals(language) || str.equals(TranslateController.UNKNOWN_LANGUAGE)) && !RestrictedLanguagesSelectActivity.getRestrictedLanguages().contains(str)) {
                TranslateAlert2.showAlert(baseFragment.getContext(), baseFragment, UserConfig.selectedAccount, inputPeer, i, str, language, charSequence, arrayList, false, callbackReturn, new Runnable() { // from class: com.exteragram.messenger.utils.text.TranslatorUtils$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        chatActivity.dimBehindView(false);
                    }
                });
            }
        }
    }

    public static void translateWithDefault(final CharSequence charSequence, TLRPC.InputPeer inputPeer, int i, final String str, ArrayList arrayList, final TranslateCallback translateCallback) {
        TLRPC.TL_messages_translateText tL_messages_translateText = new TLRPC.TL_messages_translateText();
        TLRPC.TL_textWithEntities tL_textWithEntities = new TLRPC.TL_textWithEntities();
        tL_textWithEntities.text = charSequence == null ? "" : charSequence.toString();
        if (arrayList != null) {
            tL_textWithEntities.entities = arrayList;
        }
        if (inputPeer != null) {
            tL_messages_translateText.flags |= 1;
            tL_messages_translateText.peer = inputPeer;
            tL_messages_translateText.f1696id.add(Integer.valueOf(i));
        } else {
            tL_messages_translateText.flags |= 2;
            tL_messages_translateText.text.add(tL_textWithEntities);
        }
        String str2 = str != null ? str.split("_")[0] : str;
        if ("nb".equals(str2)) {
            str2 = "no";
        }
        tL_messages_translateText.to_lang = str2;
        translateCallback.onReqId(ConnectionsManager.getInstance(UserConfig.selectedAccount).sendRequest(tL_messages_translateText, new RequestDelegate() { // from class: com.exteragram.messenger.utils.text.TranslatorUtils$$ExternalSyntheticLambda9
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                TranslatorUtils.$r8$lambda$MVJj9PFxcCXx93EbUvyH10dnaac(charSequence, str, translateCallback, tLObject, tL_error);
            }
        }));
    }

    public static /* synthetic */ void $r8$lambda$MVJj9PFxcCXx93EbUvyH10dnaac(CharSequence charSequence, String str, final TranslateCallback translateCallback, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        if (tL_error != null && "TRANSLATIONS_DISABLED_ALT".equalsIgnoreCase(tL_error.text)) {
            translate(charSequence, str, translateCallback);
            return;
        }
        if (tLObject instanceof TLRPC.TL_messages_translateResult) {
            TLRPC.TL_messages_translateResult tL_messages_translateResult = (TLRPC.TL_messages_translateResult) tLObject;
            if (!tL_messages_translateResult.result.isEmpty() && tL_messages_translateResult.result.get(0) != null && ((TLRPC.TL_textWithEntities) tL_messages_translateResult.result.get(0)).text != null) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.utils.text.TranslatorUtils$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        translateCallback.onSuccess(tLObject, tL_error);
                    }
                });
                return;
            }
        }
        Objects.requireNonNull(translateCallback);
        AndroidUtilities.runOnUIThread(new BaseTranslator$$ExternalSyntheticLambda0(translateCallback));
    }

    /* loaded from: classes3.dex */
    public interface TranslateCallback {
        void onFailed();

        void onReqId(int i);

        void onSuccess(String str);

        void onSuccess(TLObject tLObject, TLRPC.TL_error tL_error);

        /* renamed from: com.exteragram.messenger.utils.text.TranslatorUtils$TranslateCallback$-CC, reason: invalid class name */
        public abstract /* synthetic */ class CC {
            public static void $default$onSuccess(TranslateCallback translateCallback, String str) {
            }

            public static void $default$onSuccess(TranslateCallback translateCallback, TLObject tLObject, TLRPC.TL_error tL_error) {
            }

            public static void $default$onReqId(TranslateCallback translateCallback, int i) {
            }
        }
    }

    public static void translate(CharSequence charSequence, TranslateCallback translateCallback) {
        translate(charSequence, ExteraConfig.targetLang, translateCallback);
    }

    public static void translate(final CharSequence charSequence, final String str, final TranslateCallback translateCallback) {
        if (TextUtils.isEmpty(charSequence)) {
            return;
        }
        if (LanguageDetector.hasSupport()) {
            LanguageDetector.detectLanguage(charSequence.toString(), new LanguageDetector.StringCallback() { // from class: com.exteragram.messenger.utils.text.TranslatorUtils$$ExternalSyntheticLambda7
                @Override // org.telegram.messenger.LanguageDetector.StringCallback
                public final void run(String str2) {
                    TranslatorUtils.$r8$lambda$pdZ50QO5AiTMQ2NMWlk49hsifjk(charSequence, str, translateCallback, str2);
                }
            }, new LanguageDetector.ExceptionCallback() { // from class: com.exteragram.messenger.utils.text.TranslatorUtils$$ExternalSyntheticLambda8
                @Override // org.telegram.messenger.LanguageDetector.ExceptionCallback
                public final void run(Exception exc) {
                    TranslatorUtils.translate(charSequence, "auto", str, translateCallback);
                }
            });
        } else {
            translate(charSequence, "auto", str, translateCallback);
        }
    }

    public static /* synthetic */ void $r8$lambda$pdZ50QO5AiTMQ2NMWlk49hsifjk(CharSequence charSequence, String str, TranslateCallback translateCallback, String str2) {
        if (str2 == null || str2.equals(TranslateController.UNKNOWN_LANGUAGE)) {
            str2 = "auto";
        }
        translate(charSequence, str2, str, translateCallback);
    }

    public static void translate(CharSequence charSequence, String str, String str2, TranslateCallback translateCallback) {
        BaseTranslator yandexTranslator;
        int i = ExteraConfig.translationProvider;
        if (i == 2) {
            yandexTranslator = YandexTranslator.getInstance();
        } else if (i == 3) {
            yandexTranslator = DeepLTranslator.getInstance();
        } else {
            yandexTranslator = GoogleTranslator.getInstance();
        }
        if (!yandexTranslator.isLanguageSupported(str2)) {
            yandexTranslator = GoogleTranslator.getInstance();
        }
        yandexTranslator.translate(charSequence.toString(), str, str2, translateCallback);
    }
}
