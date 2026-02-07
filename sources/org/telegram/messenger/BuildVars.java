package org.telegram.messenger;

import android.os.Build;
import com.exteragram.messenger.ExteraConfig;

/* loaded from: classes.dex */
public class BuildVars {
    public static String AYU_VERSION = null;
    public static int BUILD_VERSION = 0;
    public static String BUILD_VERSION_STRING = null;
    public static boolean DEBUG_VERSION = false;
    public static String EXTERA_APP_HASH;
    public static int EXTERA_APP_ID;
    public static String GITHUB_APP_URL;
    public static String GOOGLE_AUTH_CLIENT_ID;
    public static boolean IS_BILLING_UNAVAILABLE;
    public static boolean IS_LITE_VERSION;
    public static boolean NO_SCOPED_STORAGE;
    public static int OFFICIAL_TELEGRAM_VERSION;
    public static boolean PM_BUILD;
    public static String SAFETYNET_KEY;
    public static boolean SUPPORTS_PASSKEYS;
    public static boolean LOGS_ENABLED = ExteraConfig.getLogging();
    public static boolean DEBUG_PRIVATE_VERSION = false;
    public static boolean USE_CLOUD_STRINGS = true;
    public static boolean CHECK_UPDATES = false;

    private static boolean hasDirectCurrency() {
        return false;
    }

    public static boolean useInvoiceBilling() {
        return true;
    }

    static {
        NO_SCOPED_STORAGE = Build.VERSION.SDK_INT <= 29;
        PM_BUILD = false;
        IS_LITE_VERSION = false;
        SUPPORTS_PASSKEYS = false;
        SAFETYNET_KEY = "";
        GITHUB_APP_URL = "https://t.me/ayugramreleases";
        GOOGLE_AUTH_CLIENT_ID = "760348033671-81kmi3pi84p11ub8hp9a1funsv0rn2p9.apps.googleusercontent.com";
        IS_BILLING_UNAVAILABLE = false;
        BUILD_VERSION = BuildConfig.VERSION_CODE;
        BUILD_VERSION_STRING = "12.2.10";
        OFFICIAL_TELEGRAM_VERSION = BuildConfig.OFFICIAL_TELEGRAM_VERSION;
        AYU_VERSION = BuildConfig.AYU_VERSION;
        EXTERA_APP_ID = 4;
        EXTERA_APP_HASH = BuildConfig.APP_HASH;
    }

    public static boolean isBetaApp() {
        return DEBUG_VERSION;
    }

    public static String getSmsHash() {
        return DEBUG_VERSION ? "O2P2z+/jBpJ" : "oLeq9AcOZkT";
    }
}
