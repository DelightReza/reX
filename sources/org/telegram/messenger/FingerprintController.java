package org.telegram.messenger;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import com.exteragram.messenger.utils.system.SystemUtils;
import com.yandex.runtime.attestation_storage.internal.PlatformKeystoreImpl$$ExternalSyntheticApiModelOutline0;
import com.yandex.runtime.attestation_storage.internal.PlatformKeystoreImpl$$ExternalSyntheticApiModelOutline1;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.util.Locale;
import javax.crypto.Cipher;

/* loaded from: classes.dex */
public class FingerprintController {
    private static final String KEY_ALIAS = "tmessages_passcode";
    private static Boolean hasChangedFingerprints;
    private static KeyPairGenerator keyPairGenerator;
    private static KeyStore keyStore;

    private static KeyStore getKeyStore() throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        KeyStore keyStore2 = keyStore;
        if (keyStore2 != null) {
            return keyStore2;
        }
        try {
            KeyStore keyStore3 = KeyStore.getInstance("AndroidKeyStore");
            keyStore = keyStore3;
            keyStore3.load(null);
            return keyStore;
        } catch (Exception e) {
            FileLog.m1160e(e);
            return null;
        }
    }

    private static KeyPairGenerator getKeyPairGenerator() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyPairGenerator2 = keyPairGenerator;
        if (keyPairGenerator2 != null) {
            return keyPairGenerator2;
        }
        try {
            KeyPairGenerator keyPairGenerator3 = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
            keyPairGenerator = keyPairGenerator3;
            return keyPairGenerator3;
        } catch (Exception e) {
            FileLog.m1160e(e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void generateNewKey(final boolean z) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyPairGenerator2 = getKeyPairGenerator();
        if (keyPairGenerator2 != null) {
            try {
                Locale locale = Locale.getDefault();
                setLocale(Locale.ENGLISH);
                PlatformKeystoreImpl$$ExternalSyntheticApiModelOutline1.m480m();
                keyPairGenerator2.initialize(PlatformKeystoreImpl$$ExternalSyntheticApiModelOutline0.m479m(KEY_ALIAS, 3).setDigests("SHA-256", "SHA-512").setEncryptionPaddings("OAEPPadding").setUserAuthenticationRequired(true).build());
                keyPairGenerator2.generateKeyPair();
                setLocale(locale);
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.FingerprintController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.didGenerateFingerprintKeyPair, Boolean.valueOf(z));
                    }
                });
            } catch (InvalidAlgorithmParameterException e) {
                FileLog.m1160e(e);
            } catch (Exception e2) {
                if (e2.getClass().getName().equals("android.security.KeyStoreException")) {
                    return;
                }
                FileLog.m1160e(e2);
            }
        }
    }

    public static void deleteInvalidKey() {
        try {
            getKeyStore().deleteEntry(KEY_ALIAS);
        } catch (KeyStoreException e) {
            FileLog.m1160e(e);
        }
        hasChangedFingerprints = null;
        checkKeyReady(false);
    }

    public static void checkKeyReady() {
        checkKeyReady(true);
    }

    public static void checkKeyReady(final boolean z) {
        if (!isKeyReady() && AndroidUtilities.isKeyguardSecure() && SystemUtils.hasBiometrics()) {
            Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FingerprintController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
                    FingerprintController.generateNewKey(z);
                }
            });
        }
    }

    public static boolean isKeyReady() {
        try {
            return getKeyStore().containsAlias(KEY_ALIAS);
        } catch (KeyStoreException e) {
            FileLog.m1160e(e);
            return false;
        }
    }

    public static boolean checkDeviceFingerprintsChanged() throws InvalidKeyException {
        Boolean bool = hasChangedFingerprints;
        if (bool != null) {
            return bool.booleanValue();
        }
        try {
            Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding").init(2, keyStore.getKey(KEY_ALIAS, null));
            hasChangedFingerprints = Boolean.FALSE;
            return false;
        } catch (KeyPermanentlyInvalidatedException unused) {
            hasChangedFingerprints = Boolean.TRUE;
            return true;
        } catch (Exception e) {
            FileLog.m1160e(e);
            hasChangedFingerprints = Boolean.FALSE;
            return false;
        }
    }

    private static void setLocale(Locale locale) {
        Locale.setDefault(locale);
        Resources resources = ApplicationLoader.applicationContext.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
