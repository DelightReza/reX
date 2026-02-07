package com.radolyn.ayugram;

import com.radolyn.ayugram.utils.fcm.CloudMessagingUtils;
import java.io.File;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Formatter;
import org.lsposed.lsparanoid.Deobfuscator$AyuGram4A$TMessagesProj;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.FileLog;

/* loaded from: classes.dex */
public abstract class AyuInfra {
    private static Boolean isModified;
    private static final String[] EXPECTED_SIGNATURES = {Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019344936322858534L), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019345112416517670L)};
    private static final String[] EXPECTED_PACKAGE_NAMES = {Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019345288510176806L), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019345374409522726L), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019345473193770534L)};

    public static void init() {
        AyuWorker.run();
        initializeAttachmentsFolder();
        if (isModified()) {
            FileLog.m1157d(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019344322142535206L));
        }
    }

    public static boolean isModified() {
        if (isModified == null) {
            isModified = Boolean.valueOf(isAppModified());
        }
        return isModified.booleanValue();
    }

    public static String getVersionString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019344408041881126L));
        if (BuildVars.IS_LITE_VERSION) {
            sb.append(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019344442401619494L));
        }
        sb.append(' ');
        sb.append(BuildVars.AYU_VERSION);
        if (CloudMessagingUtils.spoofingNeeded()) {
            sb.append(' ');
            sb.append(getPackageVersion().toUpperCase());
        }
        if (isModified()) {
            sb.append(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019344468171423270L));
        }
        return sb.toString();
    }

    public static void initializeAttachmentsFolder() {
        File file = new File(AyuConfig.getSavePathJava(), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019344502531161638L));
        try {
            AyuConfig.getSavePathJava().mkdirs();
            if (!AyuConfig.getSavePathJava().isDirectory() || file.exists()) {
                return;
            }
            AndroidUtilities.createEmptyFile(file);
            if (file.exists()) {
                return;
            }
            File file2 = new File(AyuConfig.getSavePathJava(), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019344541185867302L) + AyuUtils.generateRandomString(3));
            AndroidUtilities.createEmptyFile(file2);
            if (!file2.exists() || file2.renameTo(file) || file2.delete()) {
                return;
            }
            file2.deleteOnExit();
        } catch (Throwable th) {
            FileLog.m1160e(th);
        }
    }

    public static String getPackageVersion() {
        String packageName = AyuUtils.getPackageName();
        int iHashCode = packageName.hashCode();
        if (iHashCode != -1897170512) {
            if (iHashCode == -733096426 && packageName.equals(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019344691509722662L))) {
                return Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019344824653708838L);
            }
        } else if (packageName.equals(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019344592725474854L))) {
            return Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019344807473839654L);
        }
        return Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019344854718479910L);
    }

    private static boolean isAppModified() {
        try {
            String str = ApplicationLoader.applicationLoaderInstance.getPackageManager().getPackageInfo(ApplicationLoader.applicationContext.getPackageName(), 64).packageName;
            byte[] bArrDigest = MessageDigest.getInstance(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019344889078218278L)).digest(ApplicationLoader.applicationLoaderInstance.getAyuPackageManager().getOriginalSignature().toByteArray());
            Formatter formatter = new Formatter();
            for (byte b : bArrDigest) {
                formatter.format(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019344914848022054L), Byte.valueOf(b));
            }
            String string = formatter.toString();
            if (Arrays.asList(EXPECTED_PACKAGE_NAMES).contains(str)) {
                if (Arrays.asList(EXPECTED_SIGNATURES).contains(string)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            FileLog.m1160e(e);
            return true;
        }
    }
}
