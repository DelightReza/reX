package com.radolyn.ayugram.utils.fcm;

import android.content.Context;
import com.google.firebase.FirebaseOptions;
import org.lsposed.lsparanoid.Deobfuscator$AyuGram4A$TMessagesProj;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;

/* loaded from: classes.dex */
public abstract class CloudMessagingUtils {
    public static boolean isSignatureSpoofed() {
        try {
            Context context = ApplicationLoader.applicationContext;
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures[0].toCharsString().equals(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019337660648259110L));
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean spoofingNeeded() {
        return spoofingNeeded(ApplicationLoader.applicationContext);
    }

    public static boolean spoofingNeeded(Context context) {
        if (context == null) {
            context = AndroidUtilities.getActivity();
        }
        if (context == null) {
            return false;
        }
        String packageName = context.getPackageName();
        return packageName.equals(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019342294917971494L)) || packageName.equals(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019342393702219302L));
    }

    public static FirebaseOptions getConfig() {
        String packageName = ApplicationLoader.applicationContext.getPackageName();
        if (packageName.equals(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019342509666336294L)) || packageName.equals(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019342608450584102L))) {
            return new FirebaseOptions.Builder().setApplicationId(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019342724414701094L)).setApiKey(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019342921983196710L)).setStorageBucket(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019343093781888550L)).setDatabaseUrl(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019343192566136358L)).setProjectId(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019343338595024422L)).setGcmSenderId(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019343385839664678L)).build();
        }
        throw new RuntimeException(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019343441674239526L) + packageName);
    }

    public static boolean isFcmCallBypass() {
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            if (stackTraceElement.getMethodName().contains(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019343583408160294L)) || stackTraceElement.getMethodName().contains(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019343665012538918L))) {
                return true;
            }
            if ((stackTraceElement.getMethodName().contains(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019343742321950246L)) && stackTraceElement.getClassName().contains(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019343798156525094L))) || stackTraceElement.getMethodName().contains(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019343841106198054L))) {
                return true;
            }
        }
        return false;
    }
}
