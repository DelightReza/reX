package org.telegram.messenger.support.fingerprint;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import com.exteragram.messenger.utils.system.SystemUtils$$ExternalSyntheticApiModelOutline3;
import org.telegram.messenger.FileLog;

/* loaded from: classes4.dex */
public abstract class FingerprintManagerCompatApi23 {
    private static FingerprintManager getFingerprintManager(Context context) {
        return SystemUtils$$ExternalSyntheticApiModelOutline3.m241m(context.getSystemService("fingerprint"));
    }

    public static boolean hasEnrolledFingerprints(Context context) {
        try {
            FingerprintManager fingerprintManager = getFingerprintManager(context);
            if (fingerprintManager == null) {
                return false;
            }
            return fingerprintManager.hasEnrolledFingerprints();
        } catch (Exception e) {
            FileLog.m1160e(e);
            return false;
        }
    }

    public static boolean isHardwareDetected(Context context) {
        try {
            FingerprintManager fingerprintManager = getFingerprintManager(context);
            if (fingerprintManager == null) {
                return false;
            }
            return fingerprintManager.isHardwareDetected();
        } catch (Exception e) {
            FileLog.m1160e(e);
            return false;
        }
    }
}
