package org.telegram.messenger;

import android.os.SystemClock;
import android.text.TextUtils;
import androidx.annotation.Keep;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.radolyn.ayugram.AyuState;
import com.radolyn.ayugram.utils.fcm.CloudMessagingUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.CountDownLatch;
import org.json.JSONException;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC;

@Keep
/* loaded from: classes.dex */
public class PushListenerController {
    public static final int NOTIFICATION_ID = 1;
    public static final int PUSH_TYPE_FIREBASE = 2;
    public static final int PUSH_TYPE_HUAWEI = 13;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    @Keep
    public interface IPushListenerServiceProvider {
        String getLogTitle();

        int getPushType();

        boolean hasServices();

        void onRequestPushToken();
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface PushType {
    }

    public static void sendRegistrationToServer(final int i, final String str) {
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.PushListenerController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PushListenerController.m3935$r8$lambda$gWkEZv3oxmETvqnhw1v3CFWl4(str, i);
            }
        });
    }

    /* renamed from: $r8$lambda$gWkEZv3oxmETvqnhw1v-3CF-Wl4, reason: not valid java name */
    public static /* synthetic */ void m3935$r8$lambda$gWkEZv3oxmETvqnhw1v3CFWl4(final String str, final int i) {
        boolean z;
        ConnectionsManager.setRegId(str, i, SharedConfig.pushStringStatus);
        if (str == null) {
            return;
        }
        if (SharedConfig.pushStringGetTimeStart == 0 || SharedConfig.pushStringGetTimeEnd == 0 || (SharedConfig.pushStatSent && TextUtils.equals(SharedConfig.pushString, str))) {
            z = false;
        } else {
            SharedConfig.pushStatSent = false;
            z = true;
        }
        SharedConfig.pushString = str;
        SharedConfig.pushType = i;
        for (final int i2 = 0; i2 < 16; i2++) {
            UserConfig userConfig = UserConfig.getInstance(i2);
            userConfig.registeredForPush = false;
            userConfig.saveConfig(false);
            if (userConfig.getClientUserId() != 0) {
                if (z) {
                    String str2 = i == 2 ? "fcm" : "hcm";
                    TLRPC.TL_help_saveAppLog tL_help_saveAppLog = new TLRPC.TL_help_saveAppLog();
                    TLRPC.TL_inputAppEvent tL_inputAppEvent = new TLRPC.TL_inputAppEvent();
                    tL_inputAppEvent.time = SharedConfig.pushStringGetTimeStart;
                    tL_inputAppEvent.type = str2 + "_token_request";
                    tL_inputAppEvent.peer = 0L;
                    tL_inputAppEvent.data = new TLRPC.TL_jsonNull();
                    tL_help_saveAppLog.events.add(tL_inputAppEvent);
                    TLRPC.TL_inputAppEvent tL_inputAppEvent2 = new TLRPC.TL_inputAppEvent();
                    tL_inputAppEvent2.time = SharedConfig.pushStringGetTimeEnd;
                    tL_inputAppEvent2.type = str2 + "_token_response";
                    tL_inputAppEvent2.peer = SharedConfig.pushStringGetTimeEnd - SharedConfig.pushStringGetTimeStart;
                    tL_inputAppEvent2.data = new TLRPC.TL_jsonNull();
                    tL_help_saveAppLog.events.add(tL_inputAppEvent2);
                    SharedConfig.pushStatSent = true;
                    SharedConfig.saveConfig();
                    ConnectionsManager.getInstance(i2).sendRequest(tL_help_saveAppLog, null);
                    z = false;
                }
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.PushListenerController$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        MessagesController.getInstance(i2).registerForPush(i, str);
                    }
                });
            }
        }
    }

    public static void processRemoteMessage(int i, final String str, final long j) {
        final String str2 = i == 2 ? "FCM" : "HCM";
        if (BuildVars.LOGS_ENABLED) {
            FileLog.m1157d(str2 + " PRE START PROCESSING");
        }
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.PushListenerController$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                PushListenerController.$r8$lambda$nYIZ4V9UJXbKQeRZEvfo7jiLnHo(str2, str, j);
            }
        });
        try {
            countDownLatch.await();
        } catch (Throwable unused) {
        }
        if (BuildVars.DEBUG_VERSION) {
            FileLog.m1157d("finished " + str2 + " service, time = " + (SystemClock.elapsedRealtime() - jElapsedRealtime));
        }
    }

    public static /* synthetic */ void $r8$lambda$nYIZ4V9UJXbKQeRZEvfo7jiLnHo(final String str, final String str2, final long j) {
        if (BuildVars.LOGS_ENABLED) {
            FileLog.m1157d(str + " PRE INIT APP");
        }
        ApplicationLoader.postInitApplication();
        if (BuildVars.LOGS_ENABLED) {
            FileLog.m1157d(str + " POST INIT APP");
        }
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.PushListenerController$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() throws JSONException {
                PushListenerController.lambda$processRemoteMessage$6(str, str2, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:399:0x098f, code lost:
    
        if (r6.equals("LOCKED_MESSAGE") != false) goto L400;
     */
    /* JADX WARN: Code restructure failed: missing block: B:829:0x1bdf, code lost:
    
        if (r6.equals("CHANNEL_MESSAGE_TEXT") != false) goto L830;
     */
    /* JADX WARN: Code restructure failed: missing block: B:888:0x1e2d, code lost:
    
        if (r6.equals("MESSAGE_MUTED") != false) goto L889;
     */
    /* JADX WARN: Code restructure failed: missing block: B:946:0x20b8, code lost:
    
        if (r6.equals("CHAT_CREATED") != false) goto L947;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:1041:0x231a A[Catch: all -> 0x039c, TryCatch #13 {all -> 0x039c, blocks: (B:151:0x0397, B:157:0x03a4, B:159:0x03ad, B:167:0x03c1, B:169:0x03c9, B:959:0x2149, B:961:0x214d, B:964:0x2173, B:966:0x2177, B:969:0x21a5, B:973:0x21b5, B:977:0x21c1, B:979:0x21ca, B:981:0x21d3, B:982:0x21da, B:984:0x21e2, B:989:0x220f, B:991:0x221b, B:1003:0x2251, B:1008:0x2270, B:1009:0x2284, B:1011:0x228e, B:1013:0x2296, B:1017:0x22a1, B:1019:0x22a9, B:1024:0x22b3, B:1026:0x22eb, B:1028:0x22ef, B:1030:0x22f3, B:1032:0x22f7, B:1037:0x2301, B:1038:0x2309, B:1065:0x2391, B:994:0x222b, B:996:0x2239, B:997:0x2245, B:987:0x21f6, B:988:0x2202, B:953:0x2104, B:947:0x20ba, B:864:0x1d62, B:866:0x1d6e, B:868:0x1d7e, B:871:0x1d92, B:874:0x1d9e, B:876:0x1da8, B:878:0x1dc2, B:880:0x1dcc, B:881:0x1de3, B:883:0x1ded, B:884:0x1e04, B:886:0x1e0e, B:887:0x1e25, B:890:0x1e31, B:892:0x1e3b, B:893:0x1e51, B:895:0x1e5b, B:896:0x1e79, B:898:0x1e83, B:900:0x1e9c, B:902:0x1ea6, B:903:0x1ec4, B:905:0x1ece, B:906:0x1ee6, B:908:0x1ef0, B:909:0x1f08, B:912:0x1f14, B:915:0x1f1c, B:916:0x1f4a, B:918:0x1f54, B:919:0x1f70, B:921:0x1f7a, B:922:0x1f92, B:924:0x1f9c, B:925:0x1fba, B:927:0x1fc4, B:928:0x1fdc, B:930:0x1fe6, B:931:0x1ffe, B:933:0x2008, B:934:0x2020, B:936:0x202a, B:937:0x2050, B:939:0x205c, B:941:0x2060, B:943:0x2068, B:944:0x2098, B:945:0x20b0, B:948:0x20d2, B:950:0x20dc, B:951:0x20fa, B:955:0x212f, B:957:0x213a, B:963:0x216b, B:1041:0x231a, B:1043:0x2326, B:1044:0x2330, B:1046:0x233e, B:1048:0x234b, B:1050:0x2355, B:1052:0x2366, B:1054:0x236a, B:1056:0x236e, B:1058:0x2375, B:1063:0x2387, B:1064:0x238c), top: B:1103:0x0397 }] */
    /* JADX WARN: Removed duplicated region for block: B:1046:0x233e A[Catch: all -> 0x039c, TryCatch #13 {all -> 0x039c, blocks: (B:151:0x0397, B:157:0x03a4, B:159:0x03ad, B:167:0x03c1, B:169:0x03c9, B:959:0x2149, B:961:0x214d, B:964:0x2173, B:966:0x2177, B:969:0x21a5, B:973:0x21b5, B:977:0x21c1, B:979:0x21ca, B:981:0x21d3, B:982:0x21da, B:984:0x21e2, B:989:0x220f, B:991:0x221b, B:1003:0x2251, B:1008:0x2270, B:1009:0x2284, B:1011:0x228e, B:1013:0x2296, B:1017:0x22a1, B:1019:0x22a9, B:1024:0x22b3, B:1026:0x22eb, B:1028:0x22ef, B:1030:0x22f3, B:1032:0x22f7, B:1037:0x2301, B:1038:0x2309, B:1065:0x2391, B:994:0x222b, B:996:0x2239, B:997:0x2245, B:987:0x21f6, B:988:0x2202, B:953:0x2104, B:947:0x20ba, B:864:0x1d62, B:866:0x1d6e, B:868:0x1d7e, B:871:0x1d92, B:874:0x1d9e, B:876:0x1da8, B:878:0x1dc2, B:880:0x1dcc, B:881:0x1de3, B:883:0x1ded, B:884:0x1e04, B:886:0x1e0e, B:887:0x1e25, B:890:0x1e31, B:892:0x1e3b, B:893:0x1e51, B:895:0x1e5b, B:896:0x1e79, B:898:0x1e83, B:900:0x1e9c, B:902:0x1ea6, B:903:0x1ec4, B:905:0x1ece, B:906:0x1ee6, B:908:0x1ef0, B:909:0x1f08, B:912:0x1f14, B:915:0x1f1c, B:916:0x1f4a, B:918:0x1f54, B:919:0x1f70, B:921:0x1f7a, B:922:0x1f92, B:924:0x1f9c, B:925:0x1fba, B:927:0x1fc4, B:928:0x1fdc, B:930:0x1fe6, B:931:0x1ffe, B:933:0x2008, B:934:0x2020, B:936:0x202a, B:937:0x2050, B:939:0x205c, B:941:0x2060, B:943:0x2068, B:944:0x2098, B:945:0x20b0, B:948:0x20d2, B:950:0x20dc, B:951:0x20fa, B:955:0x212f, B:957:0x213a, B:963:0x216b, B:1041:0x231a, B:1043:0x2326, B:1044:0x2330, B:1046:0x233e, B:1048:0x234b, B:1050:0x2355, B:1052:0x2366, B:1054:0x236a, B:1056:0x236e, B:1058:0x2375, B:1063:0x2387, B:1064:0x238c), top: B:1103:0x0397 }] */
    /* JADX WARN: Removed duplicated region for block: B:1049:0x2354  */
    /* JADX WARN: Removed duplicated region for block: B:1052:0x2366 A[Catch: all -> 0x039c, TryCatch #13 {all -> 0x039c, blocks: (B:151:0x0397, B:157:0x03a4, B:159:0x03ad, B:167:0x03c1, B:169:0x03c9, B:959:0x2149, B:961:0x214d, B:964:0x2173, B:966:0x2177, B:969:0x21a5, B:973:0x21b5, B:977:0x21c1, B:979:0x21ca, B:981:0x21d3, B:982:0x21da, B:984:0x21e2, B:989:0x220f, B:991:0x221b, B:1003:0x2251, B:1008:0x2270, B:1009:0x2284, B:1011:0x228e, B:1013:0x2296, B:1017:0x22a1, B:1019:0x22a9, B:1024:0x22b3, B:1026:0x22eb, B:1028:0x22ef, B:1030:0x22f3, B:1032:0x22f7, B:1037:0x2301, B:1038:0x2309, B:1065:0x2391, B:994:0x222b, B:996:0x2239, B:997:0x2245, B:987:0x21f6, B:988:0x2202, B:953:0x2104, B:947:0x20ba, B:864:0x1d62, B:866:0x1d6e, B:868:0x1d7e, B:871:0x1d92, B:874:0x1d9e, B:876:0x1da8, B:878:0x1dc2, B:880:0x1dcc, B:881:0x1de3, B:883:0x1ded, B:884:0x1e04, B:886:0x1e0e, B:887:0x1e25, B:890:0x1e31, B:892:0x1e3b, B:893:0x1e51, B:895:0x1e5b, B:896:0x1e79, B:898:0x1e83, B:900:0x1e9c, B:902:0x1ea6, B:903:0x1ec4, B:905:0x1ece, B:906:0x1ee6, B:908:0x1ef0, B:909:0x1f08, B:912:0x1f14, B:915:0x1f1c, B:916:0x1f4a, B:918:0x1f54, B:919:0x1f70, B:921:0x1f7a, B:922:0x1f92, B:924:0x1f9c, B:925:0x1fba, B:927:0x1fc4, B:928:0x1fdc, B:930:0x1fe6, B:931:0x1ffe, B:933:0x2008, B:934:0x2020, B:936:0x202a, B:937:0x2050, B:939:0x205c, B:941:0x2060, B:943:0x2068, B:944:0x2098, B:945:0x20b0, B:948:0x20d2, B:950:0x20dc, B:951:0x20fa, B:955:0x212f, B:957:0x213a, B:963:0x216b, B:1041:0x231a, B:1043:0x2326, B:1044:0x2330, B:1046:0x233e, B:1048:0x234b, B:1050:0x2355, B:1052:0x2366, B:1054:0x236a, B:1056:0x236e, B:1058:0x2375, B:1063:0x2387, B:1064:0x238c), top: B:1103:0x0397 }] */
    /* JADX WARN: Removed duplicated region for block: B:1062:0x2385  */
    /* JADX WARN: Removed duplicated region for block: B:1064:0x238c A[Catch: all -> 0x039c, PHI: r15
      0x238c: PHI (r15v16 int) = (r15v15 int), (r15v24 int), (r15v25 int), (r15v27 int), (r15v27 int), (r15v30 int), (r15v87 int) binds: [B:171:0x03d1, B:1063:0x2387, B:1061:0x2382, B:1042:0x2324, B:1043:0x2326, B:968:0x21a3, B:198:0x0487] A[DONT_GENERATE, DONT_INLINE], TryCatch #13 {all -> 0x039c, blocks: (B:151:0x0397, B:157:0x03a4, B:159:0x03ad, B:167:0x03c1, B:169:0x03c9, B:959:0x2149, B:961:0x214d, B:964:0x2173, B:966:0x2177, B:969:0x21a5, B:973:0x21b5, B:977:0x21c1, B:979:0x21ca, B:981:0x21d3, B:982:0x21da, B:984:0x21e2, B:989:0x220f, B:991:0x221b, B:1003:0x2251, B:1008:0x2270, B:1009:0x2284, B:1011:0x228e, B:1013:0x2296, B:1017:0x22a1, B:1019:0x22a9, B:1024:0x22b3, B:1026:0x22eb, B:1028:0x22ef, B:1030:0x22f3, B:1032:0x22f7, B:1037:0x2301, B:1038:0x2309, B:1065:0x2391, B:994:0x222b, B:996:0x2239, B:997:0x2245, B:987:0x21f6, B:988:0x2202, B:953:0x2104, B:947:0x20ba, B:864:0x1d62, B:866:0x1d6e, B:868:0x1d7e, B:871:0x1d92, B:874:0x1d9e, B:876:0x1da8, B:878:0x1dc2, B:880:0x1dcc, B:881:0x1de3, B:883:0x1ded, B:884:0x1e04, B:886:0x1e0e, B:887:0x1e25, B:890:0x1e31, B:892:0x1e3b, B:893:0x1e51, B:895:0x1e5b, B:896:0x1e79, B:898:0x1e83, B:900:0x1e9c, B:902:0x1ea6, B:903:0x1ec4, B:905:0x1ece, B:906:0x1ee6, B:908:0x1ef0, B:909:0x1f08, B:912:0x1f14, B:915:0x1f1c, B:916:0x1f4a, B:918:0x1f54, B:919:0x1f70, B:921:0x1f7a, B:922:0x1f92, B:924:0x1f9c, B:925:0x1fba, B:927:0x1fc4, B:928:0x1fdc, B:930:0x1fe6, B:931:0x1ffe, B:933:0x2008, B:934:0x2020, B:936:0x202a, B:937:0x2050, B:939:0x205c, B:941:0x2060, B:943:0x2068, B:944:0x2098, B:945:0x20b0, B:948:0x20d2, B:950:0x20dc, B:951:0x20fa, B:955:0x212f, B:957:0x213a, B:963:0x216b, B:1041:0x231a, B:1043:0x2326, B:1044:0x2330, B:1046:0x233e, B:1048:0x234b, B:1050:0x2355, B:1052:0x2366, B:1054:0x236a, B:1056:0x236e, B:1058:0x2375, B:1063:0x2387, B:1064:0x238c), top: B:1103:0x0397 }] */
    /* JADX WARN: Removed duplicated region for block: B:1074:0x23b0  */
    /* JADX WARN: Removed duplicated region for block: B:1075:0x23c0  */
    /* JADX WARN: Removed duplicated region for block: B:1078:0x23c7  */
    /* JADX WARN: Removed duplicated region for block: B:1095:0x034f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0305 A[Catch: all -> 0x020f, TRY_ENTER, TRY_LEAVE, TryCatch #4 {all -> 0x020f, blocks: (B:84:0x01f6, B:86:0x01fa, B:90:0x0215, B:95:0x0221, B:100:0x0241, B:102:0x0249, B:104:0x0259, B:106:0x0261, B:108:0x02a6, B:110:0x02ae, B:112:0x02c4, B:114:0x02ca, B:118:0x02ef, B:123:0x0305, B:128:0x0318), top: B:1089:0x01f4 }] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x030d  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0318 A[Catch: all -> 0x020f, TRY_ENTER, TRY_LEAVE, TryCatch #4 {all -> 0x020f, blocks: (B:84:0x01f6, B:86:0x01fa, B:90:0x0215, B:95:0x0221, B:100:0x0241, B:102:0x0249, B:104:0x0259, B:106:0x0261, B:108:0x02a6, B:110:0x02ae, B:112:0x02c4, B:114:0x02ca, B:118:0x02ef, B:123:0x0305, B:128:0x0318), top: B:1089:0x01f4 }] */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0322  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x032d  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x033f  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x035e  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x038f A[Catch: all -> 0x03a0, TRY_LEAVE, TryCatch #0 {all -> 0x03a0, blocks: (B:139:0x0346, B:147:0x035f, B:149:0x038f), top: B:1081:0x0346 }] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x03a3  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x03ad A[Catch: all -> 0x039c, TryCatch #13 {all -> 0x039c, blocks: (B:151:0x0397, B:157:0x03a4, B:159:0x03ad, B:167:0x03c1, B:169:0x03c9, B:959:0x2149, B:961:0x214d, B:964:0x2173, B:966:0x2177, B:969:0x21a5, B:973:0x21b5, B:977:0x21c1, B:979:0x21ca, B:981:0x21d3, B:982:0x21da, B:984:0x21e2, B:989:0x220f, B:991:0x221b, B:1003:0x2251, B:1008:0x2270, B:1009:0x2284, B:1011:0x228e, B:1013:0x2296, B:1017:0x22a1, B:1019:0x22a9, B:1024:0x22b3, B:1026:0x22eb, B:1028:0x22ef, B:1030:0x22f3, B:1032:0x22f7, B:1037:0x2301, B:1038:0x2309, B:1065:0x2391, B:994:0x222b, B:996:0x2239, B:997:0x2245, B:987:0x21f6, B:988:0x2202, B:953:0x2104, B:947:0x20ba, B:864:0x1d62, B:866:0x1d6e, B:868:0x1d7e, B:871:0x1d92, B:874:0x1d9e, B:876:0x1da8, B:878:0x1dc2, B:880:0x1dcc, B:881:0x1de3, B:883:0x1ded, B:884:0x1e04, B:886:0x1e0e, B:887:0x1e25, B:890:0x1e31, B:892:0x1e3b, B:893:0x1e51, B:895:0x1e5b, B:896:0x1e79, B:898:0x1e83, B:900:0x1e9c, B:902:0x1ea6, B:903:0x1ec4, B:905:0x1ece, B:906:0x1ee6, B:908:0x1ef0, B:909:0x1f08, B:912:0x1f14, B:915:0x1f1c, B:916:0x1f4a, B:918:0x1f54, B:919:0x1f70, B:921:0x1f7a, B:922:0x1f92, B:924:0x1f9c, B:925:0x1fba, B:927:0x1fc4, B:928:0x1fdc, B:930:0x1fe6, B:931:0x1ffe, B:933:0x2008, B:934:0x2020, B:936:0x202a, B:937:0x2050, B:939:0x205c, B:941:0x2060, B:943:0x2068, B:944:0x2098, B:945:0x20b0, B:948:0x20d2, B:950:0x20dc, B:951:0x20fa, B:955:0x212f, B:957:0x213a, B:963:0x216b, B:1041:0x231a, B:1043:0x2326, B:1044:0x2330, B:1046:0x233e, B:1048:0x234b, B:1050:0x2355, B:1052:0x2366, B:1054:0x236a, B:1056:0x236e, B:1058:0x2375, B:1063:0x2387, B:1064:0x238c), top: B:1103:0x0397 }] */
    /* JADX WARN: Removed duplicated region for block: B:164:0x03bc  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x03c1 A[Catch: all -> 0x039c, TryCatch #13 {all -> 0x039c, blocks: (B:151:0x0397, B:157:0x03a4, B:159:0x03ad, B:167:0x03c1, B:169:0x03c9, B:959:0x2149, B:961:0x214d, B:964:0x2173, B:966:0x2177, B:969:0x21a5, B:973:0x21b5, B:977:0x21c1, B:979:0x21ca, B:981:0x21d3, B:982:0x21da, B:984:0x21e2, B:989:0x220f, B:991:0x221b, B:1003:0x2251, B:1008:0x2270, B:1009:0x2284, B:1011:0x228e, B:1013:0x2296, B:1017:0x22a1, B:1019:0x22a9, B:1024:0x22b3, B:1026:0x22eb, B:1028:0x22ef, B:1030:0x22f3, B:1032:0x22f7, B:1037:0x2301, B:1038:0x2309, B:1065:0x2391, B:994:0x222b, B:996:0x2239, B:997:0x2245, B:987:0x21f6, B:988:0x2202, B:953:0x2104, B:947:0x20ba, B:864:0x1d62, B:866:0x1d6e, B:868:0x1d7e, B:871:0x1d92, B:874:0x1d9e, B:876:0x1da8, B:878:0x1dc2, B:880:0x1dcc, B:881:0x1de3, B:883:0x1ded, B:884:0x1e04, B:886:0x1e0e, B:887:0x1e25, B:890:0x1e31, B:892:0x1e3b, B:893:0x1e51, B:895:0x1e5b, B:896:0x1e79, B:898:0x1e83, B:900:0x1e9c, B:902:0x1ea6, B:903:0x1ec4, B:905:0x1ece, B:906:0x1ee6, B:908:0x1ef0, B:909:0x1f08, B:912:0x1f14, B:915:0x1f1c, B:916:0x1f4a, B:918:0x1f54, B:919:0x1f70, B:921:0x1f7a, B:922:0x1f92, B:924:0x1f9c, B:925:0x1fba, B:927:0x1fc4, B:928:0x1fdc, B:930:0x1fe6, B:931:0x1ffe, B:933:0x2008, B:934:0x2020, B:936:0x202a, B:937:0x2050, B:939:0x205c, B:941:0x2060, B:943:0x2068, B:944:0x2098, B:945:0x20b0, B:948:0x20d2, B:950:0x20dc, B:951:0x20fa, B:955:0x212f, B:957:0x213a, B:963:0x216b, B:1041:0x231a, B:1043:0x2326, B:1044:0x2330, B:1046:0x233e, B:1048:0x234b, B:1050:0x2355, B:1052:0x2366, B:1054:0x236a, B:1056:0x236e, B:1058:0x2375, B:1063:0x2387, B:1064:0x238c), top: B:1103:0x0397 }] */
    /* JADX WARN: Removed duplicated region for block: B:172:0x03d3  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x05e9 A[Catch: all -> 0x043b, TRY_ENTER, TryCatch #12 {all -> 0x043b, blocks: (B:184:0x040d, B:186:0x041d, B:191:0x0445, B:197:0x0476, B:192:0x0455, B:194:0x045e, B:196:0x0471, B:195:0x0468, B:201:0x0493, B:206:0x04af, B:212:0x04cc, B:213:0x04df, B:215:0x04e2, B:216:0x04ee, B:218:0x050f, B:223:0x0544, B:224:0x055d, B:226:0x0560, B:227:0x0574, B:229:0x0590, B:236:0x05cb, B:245:0x05e9, B:248:0x0605, B:250:0x0619, B:251:0x0633, B:263:0x0655, B:269:0x066a, B:271:0x0670, B:294:0x06bf, B:301:0x06d7, B:308:0x06f8, B:310:0x0709, B:314:0x071a, B:317:0x071e, B:323:0x0735, B:325:0x0738, B:327:0x073e, B:358:0x07fd, B:360:0x0805, B:364:0x0832, B:366:0x083a, B:368:0x0867, B:370:0x086f, B:374:0x08a5, B:376:0x08ad, B:377:0x08c5, B:379:0x08cd, B:380:0x08f2, B:383:0x08fc, B:389:0x0924, B:390:0x0941, B:391:0x0958, B:393:0x0960, B:394:0x0977, B:396:0x097d, B:398:0x0989, B:401:0x0995, B:403:0x099d, B:404:0x09cb, B:406:0x09d3, B:407:0x09f1, B:409:0x09f9, B:410:0x0a0b, B:412:0x0a13, B:413:0x0a31, B:415:0x0a39, B:416:0x0a57, B:418:0x0a5f, B:419:0x0a77, B:421:0x0a7f, B:422:0x0a9d, B:424:0x0aa5, B:425:0x0ac3, B:427:0x0acb, B:428:0x0ae9, B:430:0x0af1, B:431:0x0b17, B:433:0x0b1f, B:434:0x0b45, B:437:0x0b4f, B:439:0x0b57, B:440:0x0b7b, B:442:0x0b83, B:443:0x0b99, B:445:0x0ba1, B:446:0x0bcb, B:448:0x0bd3, B:449:0x0bf9, B:451:0x0c01, B:452:0x0c2f, B:454:0x0c37, B:455:0x0c4f, B:457:0x0c57, B:458:0x0c6f, B:460:0x0c77, B:461:0x0c8f, B:463:0x0c97, B:464:0x0caf, B:466:0x0cb7, B:467:0x0cdb, B:469:0x0ce3, B:470:0x0cfb, B:472:0x0d03, B:473:0x0d31, B:475:0x0d39, B:476:0x0d55, B:479:0x0d5f, B:482:0x0d67, B:483:0x0d87, B:485:0x0d8f, B:486:0x0dad, B:488:0x0db5, B:489:0x0dcd, B:492:0x0dd7, B:494:0x0df1, B:495:0x0e09, B:496:0x0e1b, B:498:0x0e23, B:499:0x0e3f, B:830:0x1be1, B:502:0x0e49, B:504:0x0e51, B:505:0x0e6d, B:507:0x0e75, B:508:0x0e91, B:510:0x0e99, B:511:0x0eb7, B:513:0x0ebf, B:514:0x0ee7, B:516:0x0eef, B:517:0x0f15, B:519:0x0f1d, B:520:0x0f25, B:522:0x0f2d, B:523:0x0f4f, B:525:0x0f57, B:526:0x0f77, B:528:0x0f7f, B:529:0x0fa3, B:531:0x0fab, B:532:0x0fcd, B:534:0x0fd5, B:535:0x0ff9, B:537:0x1001, B:538:0x1031, B:540:0x1039, B:541:0x1067, B:544:0x1073, B:547:0x107d, B:549:0x1095, B:550:0x10ab, B:551:0x10bb, B:553:0x10c3, B:554:0x10e7, B:556:0x10ef, B:557:0x110d, B:560:0x1117, B:563:0x111f, B:564:0x1145, B:566:0x114d, B:567:0x1169, B:569:0x1171, B:570:0x118d, B:573:0x1197, B:575:0x11b1, B:576:0x11c9, B:577:0x11db, B:580:0x11e5, B:582:0x11ff, B:583:0x1217, B:584:0x1229, B:587:0x1233, B:589:0x124d, B:590:0x1265, B:591:0x1277, B:594:0x1281, B:596:0x129b, B:597:0x12b3, B:598:0x12c5, B:600:0x12cd, B:601:0x12e3, B:603:0x12eb, B:604:0x1303, B:606:0x130b, B:607:0x1323, B:609:0x132b, B:610:0x1355, B:612:0x135d, B:613:0x1375, B:615:0x137d, B:616:0x1395, B:618:0x139d, B:619:0x13b5, B:621:0x13bd, B:622:0x13d5, B:624:0x13dd, B:625:0x13f5, B:627:0x13fd, B:628:0x1415, B:630:0x141d, B:632:0x1421, B:634:0x1429, B:635:0x1463, B:636:0x1497, B:638:0x149f, B:639:0x14af, B:641:0x14b7, B:642:0x14d5, B:644:0x14dd, B:645:0x14fb, B:647:0x1503, B:648:0x1521, B:650:0x1529, B:651:0x1547, B:653:0x154f, B:654:0x1571, B:656:0x1579, B:657:0x1591, B:659:0x1599, B:660:0x15a9, B:663:0x15b5, B:667:0x15c4, B:669:0x15cc, B:670:0x15e8, B:672:0x15f0, B:677:0x1606, B:676:0x1603, B:678:0x1617, B:680:0x161f, B:681:0x162f, B:683:0x1637, B:684:0x1649, B:687:0x1653, B:690:0x165d, B:692:0x1665, B:693:0x1689, B:696:0x1693, B:698:0x169b, B:699:0x16b3, B:701:0x16bb, B:702:0x16d3, B:704:0x16db, B:705:0x16f1, B:707:0x16f9, B:708:0x1711, B:711:0x171b, B:713:0x1723, B:714:0x173b, B:717:0x1745, B:719:0x175d, B:720:0x1773, B:721:0x1783, B:724:0x178d, B:726:0x17a5, B:727:0x17bb, B:728:0x17cb, B:731:0x17d5, B:733:0x17ef, B:734:0x1807, B:735:0x1819, B:738:0x1823, B:740:0x183b, B:741:0x1851, B:742:0x1861, B:744:0x186b, B:746:0x186f, B:748:0x1877, B:749:0x18a7, B:750:0x18bf, B:752:0x18c7, B:753:0x18dd, B:756:0x18e7, B:759:0x18f1, B:761:0x18f5, B:763:0x18fd, B:764:0x1913, B:766:0x1927, B:768:0x192b, B:770:0x1933, B:771:0x194f, B:772:0x1967, B:774:0x196b, B:776:0x1973, B:777:0x1989, B:778:0x199b, B:780:0x19a1, B:781:0x19ad, B:783:0x19b5, B:784:0x19cb, B:787:0x19d5, B:789:0x19ed, B:790:0x1a09, B:791:0x1a1f, B:794:0x1a29, B:796:0x1a43, B:797:0x1a61, B:798:0x1a79, B:801:0x1a83, B:803:0x1a9b, B:804:0x1ab7, B:805:0x1acd, B:808:0x1ad7, B:810:0x1aef, B:811:0x1b0b, B:812:0x1b21, B:815:0x1b2b, B:817:0x1b43, B:818:0x1b59, B:819:0x1b69, B:821:0x1b71, B:822:0x1b95, B:824:0x1b9d, B:825:0x1bb5, B:827:0x1bbd, B:828:0x1bd9, B:831:0x1bfb, B:833:0x1c03, B:834:0x1c21, B:836:0x1c29, B:837:0x1c45, B:839:0x1c4d, B:840:0x1c65, B:842:0x1c6d, B:843:0x1c99, B:845:0x1ca1, B:846:0x1cc7, B:849:0x1cd1, B:851:0x1ce9, B:852:0x1cff, B:853:0x1d0f, B:855:0x1d17, B:856:0x1d2f, B:858:0x1d37, B:336:0x076e, B:277:0x067f, B:256:0x0640, B:240:0x05d8), top: B:1102:0x040b }] */
    /* JADX WARN: Removed duplicated region for block: B:246:0x05ff  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x0605 A[Catch: all -> 0x043b, TryCatch #12 {all -> 0x043b, blocks: (B:184:0x040d, B:186:0x041d, B:191:0x0445, B:197:0x0476, B:192:0x0455, B:194:0x045e, B:196:0x0471, B:195:0x0468, B:201:0x0493, B:206:0x04af, B:212:0x04cc, B:213:0x04df, B:215:0x04e2, B:216:0x04ee, B:218:0x050f, B:223:0x0544, B:224:0x055d, B:226:0x0560, B:227:0x0574, B:229:0x0590, B:236:0x05cb, B:245:0x05e9, B:248:0x0605, B:250:0x0619, B:251:0x0633, B:263:0x0655, B:269:0x066a, B:271:0x0670, B:294:0x06bf, B:301:0x06d7, B:308:0x06f8, B:310:0x0709, B:314:0x071a, B:317:0x071e, B:323:0x0735, B:325:0x0738, B:327:0x073e, B:358:0x07fd, B:360:0x0805, B:364:0x0832, B:366:0x083a, B:368:0x0867, B:370:0x086f, B:374:0x08a5, B:376:0x08ad, B:377:0x08c5, B:379:0x08cd, B:380:0x08f2, B:383:0x08fc, B:389:0x0924, B:390:0x0941, B:391:0x0958, B:393:0x0960, B:394:0x0977, B:396:0x097d, B:398:0x0989, B:401:0x0995, B:403:0x099d, B:404:0x09cb, B:406:0x09d3, B:407:0x09f1, B:409:0x09f9, B:410:0x0a0b, B:412:0x0a13, B:413:0x0a31, B:415:0x0a39, B:416:0x0a57, B:418:0x0a5f, B:419:0x0a77, B:421:0x0a7f, B:422:0x0a9d, B:424:0x0aa5, B:425:0x0ac3, B:427:0x0acb, B:428:0x0ae9, B:430:0x0af1, B:431:0x0b17, B:433:0x0b1f, B:434:0x0b45, B:437:0x0b4f, B:439:0x0b57, B:440:0x0b7b, B:442:0x0b83, B:443:0x0b99, B:445:0x0ba1, B:446:0x0bcb, B:448:0x0bd3, B:449:0x0bf9, B:451:0x0c01, B:452:0x0c2f, B:454:0x0c37, B:455:0x0c4f, B:457:0x0c57, B:458:0x0c6f, B:460:0x0c77, B:461:0x0c8f, B:463:0x0c97, B:464:0x0caf, B:466:0x0cb7, B:467:0x0cdb, B:469:0x0ce3, B:470:0x0cfb, B:472:0x0d03, B:473:0x0d31, B:475:0x0d39, B:476:0x0d55, B:479:0x0d5f, B:482:0x0d67, B:483:0x0d87, B:485:0x0d8f, B:486:0x0dad, B:488:0x0db5, B:489:0x0dcd, B:492:0x0dd7, B:494:0x0df1, B:495:0x0e09, B:496:0x0e1b, B:498:0x0e23, B:499:0x0e3f, B:830:0x1be1, B:502:0x0e49, B:504:0x0e51, B:505:0x0e6d, B:507:0x0e75, B:508:0x0e91, B:510:0x0e99, B:511:0x0eb7, B:513:0x0ebf, B:514:0x0ee7, B:516:0x0eef, B:517:0x0f15, B:519:0x0f1d, B:520:0x0f25, B:522:0x0f2d, B:523:0x0f4f, B:525:0x0f57, B:526:0x0f77, B:528:0x0f7f, B:529:0x0fa3, B:531:0x0fab, B:532:0x0fcd, B:534:0x0fd5, B:535:0x0ff9, B:537:0x1001, B:538:0x1031, B:540:0x1039, B:541:0x1067, B:544:0x1073, B:547:0x107d, B:549:0x1095, B:550:0x10ab, B:551:0x10bb, B:553:0x10c3, B:554:0x10e7, B:556:0x10ef, B:557:0x110d, B:560:0x1117, B:563:0x111f, B:564:0x1145, B:566:0x114d, B:567:0x1169, B:569:0x1171, B:570:0x118d, B:573:0x1197, B:575:0x11b1, B:576:0x11c9, B:577:0x11db, B:580:0x11e5, B:582:0x11ff, B:583:0x1217, B:584:0x1229, B:587:0x1233, B:589:0x124d, B:590:0x1265, B:591:0x1277, B:594:0x1281, B:596:0x129b, B:597:0x12b3, B:598:0x12c5, B:600:0x12cd, B:601:0x12e3, B:603:0x12eb, B:604:0x1303, B:606:0x130b, B:607:0x1323, B:609:0x132b, B:610:0x1355, B:612:0x135d, B:613:0x1375, B:615:0x137d, B:616:0x1395, B:618:0x139d, B:619:0x13b5, B:621:0x13bd, B:622:0x13d5, B:624:0x13dd, B:625:0x13f5, B:627:0x13fd, B:628:0x1415, B:630:0x141d, B:632:0x1421, B:634:0x1429, B:635:0x1463, B:636:0x1497, B:638:0x149f, B:639:0x14af, B:641:0x14b7, B:642:0x14d5, B:644:0x14dd, B:645:0x14fb, B:647:0x1503, B:648:0x1521, B:650:0x1529, B:651:0x1547, B:653:0x154f, B:654:0x1571, B:656:0x1579, B:657:0x1591, B:659:0x1599, B:660:0x15a9, B:663:0x15b5, B:667:0x15c4, B:669:0x15cc, B:670:0x15e8, B:672:0x15f0, B:677:0x1606, B:676:0x1603, B:678:0x1617, B:680:0x161f, B:681:0x162f, B:683:0x1637, B:684:0x1649, B:687:0x1653, B:690:0x165d, B:692:0x1665, B:693:0x1689, B:696:0x1693, B:698:0x169b, B:699:0x16b3, B:701:0x16bb, B:702:0x16d3, B:704:0x16db, B:705:0x16f1, B:707:0x16f9, B:708:0x1711, B:711:0x171b, B:713:0x1723, B:714:0x173b, B:717:0x1745, B:719:0x175d, B:720:0x1773, B:721:0x1783, B:724:0x178d, B:726:0x17a5, B:727:0x17bb, B:728:0x17cb, B:731:0x17d5, B:733:0x17ef, B:734:0x1807, B:735:0x1819, B:738:0x1823, B:740:0x183b, B:741:0x1851, B:742:0x1861, B:744:0x186b, B:746:0x186f, B:748:0x1877, B:749:0x18a7, B:750:0x18bf, B:752:0x18c7, B:753:0x18dd, B:756:0x18e7, B:759:0x18f1, B:761:0x18f5, B:763:0x18fd, B:764:0x1913, B:766:0x1927, B:768:0x192b, B:770:0x1933, B:771:0x194f, B:772:0x1967, B:774:0x196b, B:776:0x1973, B:777:0x1989, B:778:0x199b, B:780:0x19a1, B:781:0x19ad, B:783:0x19b5, B:784:0x19cb, B:787:0x19d5, B:789:0x19ed, B:790:0x1a09, B:791:0x1a1f, B:794:0x1a29, B:796:0x1a43, B:797:0x1a61, B:798:0x1a79, B:801:0x1a83, B:803:0x1a9b, B:804:0x1ab7, B:805:0x1acd, B:808:0x1ad7, B:810:0x1aef, B:811:0x1b0b, B:812:0x1b21, B:815:0x1b2b, B:817:0x1b43, B:818:0x1b59, B:819:0x1b69, B:821:0x1b71, B:822:0x1b95, B:824:0x1b9d, B:825:0x1bb5, B:827:0x1bbd, B:828:0x1bd9, B:831:0x1bfb, B:833:0x1c03, B:834:0x1c21, B:836:0x1c29, B:837:0x1c45, B:839:0x1c4d, B:840:0x1c65, B:842:0x1c6d, B:843:0x1c99, B:845:0x1ca1, B:846:0x1cc7, B:849:0x1cd1, B:851:0x1ce9, B:852:0x1cff, B:853:0x1d0f, B:855:0x1d17, B:856:0x1d2f, B:858:0x1d37, B:336:0x076e, B:277:0x067f, B:256:0x0640, B:240:0x05d8), top: B:1102:0x040b }] */
    /* JADX WARN: Removed duplicated region for block: B:254:0x063a  */
    /* JADX WARN: Removed duplicated region for block: B:265:0x065b  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x0679 A[Catch: all -> 0x1d8d, TRY_ENTER, TRY_LEAVE, TryCatch #3 {all -> 0x1d8d, blocks: (B:173:0x03d5, B:177:0x03ed, B:181:0x0401, B:199:0x048b, B:203:0x04a2, B:209:0x04bd, B:221:0x053c, B:232:0x05bd, B:234:0x05c3, B:243:0x05e0, B:260:0x064d, B:266:0x065c, B:285:0x0690, B:292:0x06b6, B:299:0x06ce, B:306:0x06f2, B:320:0x0723, B:348:0x07a9, B:350:0x07af, B:353:0x07c9, B:860:0x1d4e, B:862:0x1d56, B:338:0x077c, B:345:0x0799, B:275:0x0679, B:238:0x05d2), top: B:1087:0x03d5 }] */
    /* JADX WARN: Removed duplicated region for block: B:277:0x067f A[Catch: all -> 0x043b, TRY_ENTER, TRY_LEAVE, TryCatch #12 {all -> 0x043b, blocks: (B:184:0x040d, B:186:0x041d, B:191:0x0445, B:197:0x0476, B:192:0x0455, B:194:0x045e, B:196:0x0471, B:195:0x0468, B:201:0x0493, B:206:0x04af, B:212:0x04cc, B:213:0x04df, B:215:0x04e2, B:216:0x04ee, B:218:0x050f, B:223:0x0544, B:224:0x055d, B:226:0x0560, B:227:0x0574, B:229:0x0590, B:236:0x05cb, B:245:0x05e9, B:248:0x0605, B:250:0x0619, B:251:0x0633, B:263:0x0655, B:269:0x066a, B:271:0x0670, B:294:0x06bf, B:301:0x06d7, B:308:0x06f8, B:310:0x0709, B:314:0x071a, B:317:0x071e, B:323:0x0735, B:325:0x0738, B:327:0x073e, B:358:0x07fd, B:360:0x0805, B:364:0x0832, B:366:0x083a, B:368:0x0867, B:370:0x086f, B:374:0x08a5, B:376:0x08ad, B:377:0x08c5, B:379:0x08cd, B:380:0x08f2, B:383:0x08fc, B:389:0x0924, B:390:0x0941, B:391:0x0958, B:393:0x0960, B:394:0x0977, B:396:0x097d, B:398:0x0989, B:401:0x0995, B:403:0x099d, B:404:0x09cb, B:406:0x09d3, B:407:0x09f1, B:409:0x09f9, B:410:0x0a0b, B:412:0x0a13, B:413:0x0a31, B:415:0x0a39, B:416:0x0a57, B:418:0x0a5f, B:419:0x0a77, B:421:0x0a7f, B:422:0x0a9d, B:424:0x0aa5, B:425:0x0ac3, B:427:0x0acb, B:428:0x0ae9, B:430:0x0af1, B:431:0x0b17, B:433:0x0b1f, B:434:0x0b45, B:437:0x0b4f, B:439:0x0b57, B:440:0x0b7b, B:442:0x0b83, B:443:0x0b99, B:445:0x0ba1, B:446:0x0bcb, B:448:0x0bd3, B:449:0x0bf9, B:451:0x0c01, B:452:0x0c2f, B:454:0x0c37, B:455:0x0c4f, B:457:0x0c57, B:458:0x0c6f, B:460:0x0c77, B:461:0x0c8f, B:463:0x0c97, B:464:0x0caf, B:466:0x0cb7, B:467:0x0cdb, B:469:0x0ce3, B:470:0x0cfb, B:472:0x0d03, B:473:0x0d31, B:475:0x0d39, B:476:0x0d55, B:479:0x0d5f, B:482:0x0d67, B:483:0x0d87, B:485:0x0d8f, B:486:0x0dad, B:488:0x0db5, B:489:0x0dcd, B:492:0x0dd7, B:494:0x0df1, B:495:0x0e09, B:496:0x0e1b, B:498:0x0e23, B:499:0x0e3f, B:830:0x1be1, B:502:0x0e49, B:504:0x0e51, B:505:0x0e6d, B:507:0x0e75, B:508:0x0e91, B:510:0x0e99, B:511:0x0eb7, B:513:0x0ebf, B:514:0x0ee7, B:516:0x0eef, B:517:0x0f15, B:519:0x0f1d, B:520:0x0f25, B:522:0x0f2d, B:523:0x0f4f, B:525:0x0f57, B:526:0x0f77, B:528:0x0f7f, B:529:0x0fa3, B:531:0x0fab, B:532:0x0fcd, B:534:0x0fd5, B:535:0x0ff9, B:537:0x1001, B:538:0x1031, B:540:0x1039, B:541:0x1067, B:544:0x1073, B:547:0x107d, B:549:0x1095, B:550:0x10ab, B:551:0x10bb, B:553:0x10c3, B:554:0x10e7, B:556:0x10ef, B:557:0x110d, B:560:0x1117, B:563:0x111f, B:564:0x1145, B:566:0x114d, B:567:0x1169, B:569:0x1171, B:570:0x118d, B:573:0x1197, B:575:0x11b1, B:576:0x11c9, B:577:0x11db, B:580:0x11e5, B:582:0x11ff, B:583:0x1217, B:584:0x1229, B:587:0x1233, B:589:0x124d, B:590:0x1265, B:591:0x1277, B:594:0x1281, B:596:0x129b, B:597:0x12b3, B:598:0x12c5, B:600:0x12cd, B:601:0x12e3, B:603:0x12eb, B:604:0x1303, B:606:0x130b, B:607:0x1323, B:609:0x132b, B:610:0x1355, B:612:0x135d, B:613:0x1375, B:615:0x137d, B:616:0x1395, B:618:0x139d, B:619:0x13b5, B:621:0x13bd, B:622:0x13d5, B:624:0x13dd, B:625:0x13f5, B:627:0x13fd, B:628:0x1415, B:630:0x141d, B:632:0x1421, B:634:0x1429, B:635:0x1463, B:636:0x1497, B:638:0x149f, B:639:0x14af, B:641:0x14b7, B:642:0x14d5, B:644:0x14dd, B:645:0x14fb, B:647:0x1503, B:648:0x1521, B:650:0x1529, B:651:0x1547, B:653:0x154f, B:654:0x1571, B:656:0x1579, B:657:0x1591, B:659:0x1599, B:660:0x15a9, B:663:0x15b5, B:667:0x15c4, B:669:0x15cc, B:670:0x15e8, B:672:0x15f0, B:677:0x1606, B:676:0x1603, B:678:0x1617, B:680:0x161f, B:681:0x162f, B:683:0x1637, B:684:0x1649, B:687:0x1653, B:690:0x165d, B:692:0x1665, B:693:0x1689, B:696:0x1693, B:698:0x169b, B:699:0x16b3, B:701:0x16bb, B:702:0x16d3, B:704:0x16db, B:705:0x16f1, B:707:0x16f9, B:708:0x1711, B:711:0x171b, B:713:0x1723, B:714:0x173b, B:717:0x1745, B:719:0x175d, B:720:0x1773, B:721:0x1783, B:724:0x178d, B:726:0x17a5, B:727:0x17bb, B:728:0x17cb, B:731:0x17d5, B:733:0x17ef, B:734:0x1807, B:735:0x1819, B:738:0x1823, B:740:0x183b, B:741:0x1851, B:742:0x1861, B:744:0x186b, B:746:0x186f, B:748:0x1877, B:749:0x18a7, B:750:0x18bf, B:752:0x18c7, B:753:0x18dd, B:756:0x18e7, B:759:0x18f1, B:761:0x18f5, B:763:0x18fd, B:764:0x1913, B:766:0x1927, B:768:0x192b, B:770:0x1933, B:771:0x194f, B:772:0x1967, B:774:0x196b, B:776:0x1973, B:777:0x1989, B:778:0x199b, B:780:0x19a1, B:781:0x19ad, B:783:0x19b5, B:784:0x19cb, B:787:0x19d5, B:789:0x19ed, B:790:0x1a09, B:791:0x1a1f, B:794:0x1a29, B:796:0x1a43, B:797:0x1a61, B:798:0x1a79, B:801:0x1a83, B:803:0x1a9b, B:804:0x1ab7, B:805:0x1acd, B:808:0x1ad7, B:810:0x1aef, B:811:0x1b0b, B:812:0x1b21, B:815:0x1b2b, B:817:0x1b43, B:818:0x1b59, B:819:0x1b69, B:821:0x1b71, B:822:0x1b95, B:824:0x1b9d, B:825:0x1bb5, B:827:0x1bbd, B:828:0x1bd9, B:831:0x1bfb, B:833:0x1c03, B:834:0x1c21, B:836:0x1c29, B:837:0x1c45, B:839:0x1c4d, B:840:0x1c65, B:842:0x1c6d, B:843:0x1c99, B:845:0x1ca1, B:846:0x1cc7, B:849:0x1cd1, B:851:0x1ce9, B:852:0x1cff, B:853:0x1d0f, B:855:0x1d17, B:856:0x1d2f, B:858:0x1d37, B:336:0x076e, B:277:0x067f, B:256:0x0640, B:240:0x05d8), top: B:1102:0x040b }] */
    /* JADX WARN: Removed duplicated region for block: B:279:0x0684  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x0687  */
    /* JADX WARN: Removed duplicated region for block: B:282:0x068a  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x068e  */
    /* JADX WARN: Removed duplicated region for block: B:350:0x07af A[Catch: all -> 0x1d8d, TryCatch #3 {all -> 0x1d8d, blocks: (B:173:0x03d5, B:177:0x03ed, B:181:0x0401, B:199:0x048b, B:203:0x04a2, B:209:0x04bd, B:221:0x053c, B:232:0x05bd, B:234:0x05c3, B:243:0x05e0, B:260:0x064d, B:266:0x065c, B:285:0x0690, B:292:0x06b6, B:299:0x06ce, B:306:0x06f2, B:320:0x0723, B:348:0x07a9, B:350:0x07af, B:353:0x07c9, B:860:0x1d4e, B:862:0x1d56, B:338:0x077c, B:345:0x0799, B:275:0x0679, B:238:0x05d2), top: B:1087:0x03d5 }] */
    /* JADX WARN: Removed duplicated region for block: B:352:0x07b5  */
    /* JADX WARN: Removed duplicated region for block: B:961:0x214d A[Catch: all -> 0x039c, TryCatch #13 {all -> 0x039c, blocks: (B:151:0x0397, B:157:0x03a4, B:159:0x03ad, B:167:0x03c1, B:169:0x03c9, B:959:0x2149, B:961:0x214d, B:964:0x2173, B:966:0x2177, B:969:0x21a5, B:973:0x21b5, B:977:0x21c1, B:979:0x21ca, B:981:0x21d3, B:982:0x21da, B:984:0x21e2, B:989:0x220f, B:991:0x221b, B:1003:0x2251, B:1008:0x2270, B:1009:0x2284, B:1011:0x228e, B:1013:0x2296, B:1017:0x22a1, B:1019:0x22a9, B:1024:0x22b3, B:1026:0x22eb, B:1028:0x22ef, B:1030:0x22f3, B:1032:0x22f7, B:1037:0x2301, B:1038:0x2309, B:1065:0x2391, B:994:0x222b, B:996:0x2239, B:997:0x2245, B:987:0x21f6, B:988:0x2202, B:953:0x2104, B:947:0x20ba, B:864:0x1d62, B:866:0x1d6e, B:868:0x1d7e, B:871:0x1d92, B:874:0x1d9e, B:876:0x1da8, B:878:0x1dc2, B:880:0x1dcc, B:881:0x1de3, B:883:0x1ded, B:884:0x1e04, B:886:0x1e0e, B:887:0x1e25, B:890:0x1e31, B:892:0x1e3b, B:893:0x1e51, B:895:0x1e5b, B:896:0x1e79, B:898:0x1e83, B:900:0x1e9c, B:902:0x1ea6, B:903:0x1ec4, B:905:0x1ece, B:906:0x1ee6, B:908:0x1ef0, B:909:0x1f08, B:912:0x1f14, B:915:0x1f1c, B:916:0x1f4a, B:918:0x1f54, B:919:0x1f70, B:921:0x1f7a, B:922:0x1f92, B:924:0x1f9c, B:925:0x1fba, B:927:0x1fc4, B:928:0x1fdc, B:930:0x1fe6, B:931:0x1ffe, B:933:0x2008, B:934:0x2020, B:936:0x202a, B:937:0x2050, B:939:0x205c, B:941:0x2060, B:943:0x2068, B:944:0x2098, B:945:0x20b0, B:948:0x20d2, B:950:0x20dc, B:951:0x20fa, B:955:0x212f, B:957:0x213a, B:963:0x216b, B:1041:0x231a, B:1043:0x2326, B:1044:0x2330, B:1046:0x233e, B:1048:0x234b, B:1050:0x2355, B:1052:0x2366, B:1054:0x236a, B:1056:0x236e, B:1058:0x2375, B:1063:0x2387, B:1064:0x238c), top: B:1103:0x0397 }] */
    /* JADX WARN: Removed duplicated region for block: B:966:0x2177 A[Catch: all -> 0x039c, TryCatch #13 {all -> 0x039c, blocks: (B:151:0x0397, B:157:0x03a4, B:159:0x03ad, B:167:0x03c1, B:169:0x03c9, B:959:0x2149, B:961:0x214d, B:964:0x2173, B:966:0x2177, B:969:0x21a5, B:973:0x21b5, B:977:0x21c1, B:979:0x21ca, B:981:0x21d3, B:982:0x21da, B:984:0x21e2, B:989:0x220f, B:991:0x221b, B:1003:0x2251, B:1008:0x2270, B:1009:0x2284, B:1011:0x228e, B:1013:0x2296, B:1017:0x22a1, B:1019:0x22a9, B:1024:0x22b3, B:1026:0x22eb, B:1028:0x22ef, B:1030:0x22f3, B:1032:0x22f7, B:1037:0x2301, B:1038:0x2309, B:1065:0x2391, B:994:0x222b, B:996:0x2239, B:997:0x2245, B:987:0x21f6, B:988:0x2202, B:953:0x2104, B:947:0x20ba, B:864:0x1d62, B:866:0x1d6e, B:868:0x1d7e, B:871:0x1d92, B:874:0x1d9e, B:876:0x1da8, B:878:0x1dc2, B:880:0x1dcc, B:881:0x1de3, B:883:0x1ded, B:884:0x1e04, B:886:0x1e0e, B:887:0x1e25, B:890:0x1e31, B:892:0x1e3b, B:893:0x1e51, B:895:0x1e5b, B:896:0x1e79, B:898:0x1e83, B:900:0x1e9c, B:902:0x1ea6, B:903:0x1ec4, B:905:0x1ece, B:906:0x1ee6, B:908:0x1ef0, B:909:0x1f08, B:912:0x1f14, B:915:0x1f1c, B:916:0x1f4a, B:918:0x1f54, B:919:0x1f70, B:921:0x1f7a, B:922:0x1f92, B:924:0x1f9c, B:925:0x1fba, B:927:0x1fc4, B:928:0x1fdc, B:930:0x1fe6, B:931:0x1ffe, B:933:0x2008, B:934:0x2020, B:936:0x202a, B:937:0x2050, B:939:0x205c, B:941:0x2060, B:943:0x2068, B:944:0x2098, B:945:0x20b0, B:948:0x20d2, B:950:0x20dc, B:951:0x20fa, B:955:0x212f, B:957:0x213a, B:963:0x216b, B:1041:0x231a, B:1043:0x2326, B:1044:0x2330, B:1046:0x233e, B:1048:0x234b, B:1050:0x2355, B:1052:0x2366, B:1054:0x236a, B:1056:0x236e, B:1058:0x2375, B:1063:0x2387, B:1064:0x238c), top: B:1103:0x0397 }] */
    /* JADX WARN: Removed duplicated region for block: B:967:0x21a1  */
    /* JADX WARN: Removed duplicated region for block: B:969:0x21a5 A[Catch: all -> 0x039c, TryCatch #13 {all -> 0x039c, blocks: (B:151:0x0397, B:157:0x03a4, B:159:0x03ad, B:167:0x03c1, B:169:0x03c9, B:959:0x2149, B:961:0x214d, B:964:0x2173, B:966:0x2177, B:969:0x21a5, B:973:0x21b5, B:977:0x21c1, B:979:0x21ca, B:981:0x21d3, B:982:0x21da, B:984:0x21e2, B:989:0x220f, B:991:0x221b, B:1003:0x2251, B:1008:0x2270, B:1009:0x2284, B:1011:0x228e, B:1013:0x2296, B:1017:0x22a1, B:1019:0x22a9, B:1024:0x22b3, B:1026:0x22eb, B:1028:0x22ef, B:1030:0x22f3, B:1032:0x22f7, B:1037:0x2301, B:1038:0x2309, B:1065:0x2391, B:994:0x222b, B:996:0x2239, B:997:0x2245, B:987:0x21f6, B:988:0x2202, B:953:0x2104, B:947:0x20ba, B:864:0x1d62, B:866:0x1d6e, B:868:0x1d7e, B:871:0x1d92, B:874:0x1d9e, B:876:0x1da8, B:878:0x1dc2, B:880:0x1dcc, B:881:0x1de3, B:883:0x1ded, B:884:0x1e04, B:886:0x1e0e, B:887:0x1e25, B:890:0x1e31, B:892:0x1e3b, B:893:0x1e51, B:895:0x1e5b, B:896:0x1e79, B:898:0x1e83, B:900:0x1e9c, B:902:0x1ea6, B:903:0x1ec4, B:905:0x1ece, B:906:0x1ee6, B:908:0x1ef0, B:909:0x1f08, B:912:0x1f14, B:915:0x1f1c, B:916:0x1f4a, B:918:0x1f54, B:919:0x1f70, B:921:0x1f7a, B:922:0x1f92, B:924:0x1f9c, B:925:0x1fba, B:927:0x1fc4, B:928:0x1fdc, B:930:0x1fe6, B:931:0x1ffe, B:933:0x2008, B:934:0x2020, B:936:0x202a, B:937:0x2050, B:939:0x205c, B:941:0x2060, B:943:0x2068, B:944:0x2098, B:945:0x20b0, B:948:0x20d2, B:950:0x20dc, B:951:0x20fa, B:955:0x212f, B:957:0x213a, B:963:0x216b, B:1041:0x231a, B:1043:0x2326, B:1044:0x2330, B:1046:0x233e, B:1048:0x234b, B:1050:0x2355, B:1052:0x2366, B:1054:0x236a, B:1056:0x236e, B:1058:0x2375, B:1063:0x2387, B:1064:0x238c), top: B:1103:0x0397 }] */
    /* JADX WARN: Type inference failed for: r28v1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static /* synthetic */ void lambda$processRemoteMessage$6(java.lang.String r68, java.lang.String r69, long r70) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 9820
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.PushListenerController.lambda$processRemoteMessage$6(java.lang.String, java.lang.String, long):void");
    }

    public static /* synthetic */ void $r8$lambda$P1a24T7VvkcTjlUqZlJxlqIf4HM(int i) {
        if (UserConfig.getInstance(i).getClientUserId() != 0) {
            AyuState.setSessionTerminated(i, UserConfig.getInstance(i).getClientUserId());
        }
    }

    private static String getReactedText(String str, Object[] objArr) {
        str.getClass();
        switch (str) {
            case "CHAT_REACT_CONTACT":
                return LocaleController.formatString(C2369R.string.PushChatReactContact, objArr);
            case "REACT_GEOLIVE":
                return LocaleController.formatString(C2369R.string.PushReactGeoLocation, objArr);
            case "REACT_STORY_HIDDEN":
                return LocaleController.formatString(C2369R.string.PushReactStoryHidden, objArr);
            case "REACT_HIDDEN":
                return LocaleController.formatString(C2369R.string.PushReactHidden, objArr);
            case "CHAT_REACT_NOTEXT":
                return LocaleController.formatString(C2369R.string.PushChatReactNotext, objArr);
            case "REACT_NOTEXT":
                return LocaleController.formatString(C2369R.string.PushReactNoText, objArr);
            case "CHAT_REACT_INVOICE":
                return LocaleController.formatString(C2369R.string.PushChatReactInvoice, objArr);
            case "REACT_CONTACT":
                return LocaleController.formatString(C2369R.string.PushReactContect, objArr);
            case "CHAT_REACT_STICKER":
                return LocaleController.formatString(C2369R.string.PushChatReactSticker, objArr);
            case "REACT_GAME":
                return LocaleController.formatString(C2369R.string.PushReactGame, objArr);
            case "REACT_POLL":
                return LocaleController.formatString(C2369R.string.PushReactPoll, objArr);
            case "REACT_QUIZ":
                return LocaleController.formatString(C2369R.string.PushReactQuiz, objArr);
            case "REACT_TEXT":
                return LocaleController.formatString(C2369R.string.PushReactText, objArr);
            case "REACT_TODO":
                return LocaleController.formatString(C2369R.string.PushReactTodo, objArr);
            case "REACT_INVOICE":
                return LocaleController.formatString(C2369R.string.PushReactInvoice, objArr);
            case "CHAT_REACT_DOC":
                return LocaleController.formatString(C2369R.string.PushChatReactDoc, objArr);
            case "CHAT_REACT_GEO":
                return LocaleController.formatString(C2369R.string.PushChatReactGeo, objArr);
            case "CHAT_REACT_GIF":
                return LocaleController.formatString(C2369R.string.PushChatReactGif, objArr);
            case "REACT_STICKER":
                return LocaleController.formatString(C2369R.string.PushReactSticker, objArr);
            case "CHAT_REACT_AUDIO":
                return LocaleController.formatString(C2369R.string.PushChatReactAudio, objArr);
            case "CHAT_REACT_PHOTO":
                return LocaleController.formatString(C2369R.string.PushChatReactPhoto, objArr);
            case "CHAT_REACT_ROUND":
                return LocaleController.formatString(C2369R.string.PushChatReactRound, objArr);
            case "CHAT_REACT_VIDEO":
                return LocaleController.formatString(C2369R.string.PushChatReactVideo, objArr);
            case "CHAT_REACT_GIVEAWAY":
                return LocaleController.formatString(C2369R.string.NotificationChatReactGiveaway, objArr);
            case "REACT_GIVEAWAY":
                return LocaleController.formatString(C2369R.string.NotificationReactGiveaway, objArr);
            case "CHAT_REACT_GEOLIVE":
                return LocaleController.formatString(C2369R.string.PushChatReactGeoLive, objArr);
            case "REACT_AUDIO":
                return LocaleController.formatString(C2369R.string.PushReactAudio, objArr);
            case "REACT_PHOTO":
                return LocaleController.formatString(C2369R.string.PushReactPhoto, objArr);
            case "REACT_ROUND":
                return LocaleController.formatString(C2369R.string.PushReactRound, objArr);
            case "REACT_STORY":
                return LocaleController.formatString(C2369R.string.PushReactStory, objArr);
            case "REACT_VIDEO":
                return LocaleController.formatString(C2369R.string.PushReactVideo, objArr);
            case "REACT_DOC":
                return LocaleController.formatString(C2369R.string.PushReactDoc, objArr);
            case "REACT_GEO":
                return LocaleController.formatString(C2369R.string.PushReactGeo, objArr);
            case "REACT_GIF":
                return LocaleController.formatString(C2369R.string.PushReactGif, objArr);
            case "CHAT_REACT_GAME":
                return LocaleController.formatString(C2369R.string.PushChatReactGame, objArr);
            case "CHAT_REACT_POLL":
                return LocaleController.formatString(C2369R.string.PushChatReactPoll, objArr);
            case "CHAT_REACT_QUIZ":
                return LocaleController.formatString(C2369R.string.PushChatReactQuiz, objArr);
            case "CHAT_REACT_TEXT":
                return LocaleController.formatString(C2369R.string.PushChatReactText, objArr);
            case "CHAT_REACT_TODO":
                return LocaleController.formatString(C2369R.string.PushChatReactTodo, objArr);
            default:
                return null;
        }
    }

    private static void onDecryptError() {
        for (int i = 0; i < 16; i++) {
            if (UserConfig.getInstance(i).isClientActivated()) {
                ConnectionsManager.onInternalPushReceived(i);
                ConnectionsManager.getInstance(i).resumeNetworkMaybe();
            }
        }
        countDownLatch.countDown();
    }

    public static final class GooglePushListenerServiceProvider implements IPushListenerServiceProvider {
        public static final GooglePushListenerServiceProvider INSTANCE = new GooglePushListenerServiceProvider();
        private Boolean hasServices;

        @Override // org.telegram.messenger.PushListenerController.IPushListenerServiceProvider
        public int getPushType() {
            return 2;
        }

        private GooglePushListenerServiceProvider() {
        }

        @Override // org.telegram.messenger.PushListenerController.IPushListenerServiceProvider
        public String getLogTitle() {
            return "Google Play Services";
        }

        @Override // org.telegram.messenger.PushListenerController.IPushListenerServiceProvider
        public void onRequestPushToken() {
            if (CloudMessagingUtils.spoofingNeeded() && hasServices()) {
                String str = SharedConfig.pushString;
                if (!TextUtils.isEmpty(str)) {
                    if (BuildVars.DEBUG_PRIVATE_VERSION && BuildVars.LOGS_ENABLED) {
                        FileLog.m1157d("FCM regId = " + str);
                    }
                } else if (BuildVars.LOGS_ENABLED) {
                    FileLog.m1157d("FCM Registration not found.");
                }
                Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.PushListenerController$GooglePushListenerServiceProvider$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onRequestPushToken$1();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestPushToken$1() {
            try {
                SharedConfig.pushStringGetTimeStart = SystemClock.elapsedRealtime();
                FirebaseApp.initializeApp(ApplicationLoader.applicationContext, CloudMessagingUtils.getConfig(), "AyuGramFCM");
                ((FirebaseMessaging) FirebaseApp.getInstance("AyuGramFCM").get(FirebaseMessaging.class)).getToken().addOnCompleteListener(new OnCompleteListener() { // from class: org.telegram.messenger.PushListenerController$GooglePushListenerServiceProvider$$ExternalSyntheticLambda0
                    @Override // com.google.android.gms.tasks.OnCompleteListener
                    public final void onComplete(Task task) {
                        this.f$0.lambda$onRequestPushToken$0(task);
                    }
                });
            } catch (Throwable th) {
                FileLog.m1160e(th);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestPushToken$0(Task task) {
            SharedConfig.pushStringGetTimeEnd = SystemClock.elapsedRealtime();
            if (!task.isSuccessful()) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.m1157d("Failed to get regid");
                }
                SharedConfig.pushStringStatus = "__FIREBASE_FAILED__";
                PushListenerController.sendRegistrationToServer(getPushType(), null);
                return;
            }
            String str = (String) task.getResult();
            if (TextUtils.isEmpty(str)) {
                return;
            }
            PushListenerController.sendRegistrationToServer(getPushType(), str);
        }

        @Override // org.telegram.messenger.PushListenerController.IPushListenerServiceProvider
        public boolean hasServices() {
            if (this.hasServices == null) {
                if (!CloudMessagingUtils.spoofingNeeded()) {
                    this.hasServices = Boolean.FALSE;
                    return false;
                }
                if (!CloudMessagingUtils.isSignatureSpoofed()) {
                    this.hasServices = Boolean.FALSE;
                    return false;
                }
                try {
                    this.hasServices = Boolean.valueOf(GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(ApplicationLoader.applicationContext) == 0);
                } catch (Exception e) {
                    FileLog.m1160e(e);
                    this.hasServices = Boolean.FALSE;
                }
            }
            return this.hasServices.booleanValue();
        }
    }
}
