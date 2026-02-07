package com.radolyn.ayugram.utils;

import com.exteragram.messenger.backup.PreferencesUtils$$ExternalSyntheticBackport1;
import com.exteragram.messenger.utils.network.RemoteUtils;
import com.radolyn.ayugram.utils.fcm.IntegrityServiceException;
import java.util.Set;
import org.lsposed.lsparanoid.Deobfuscator$AyuGram4A$TMessagesProj;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.LoginActivity;

/* loaded from: classes4.dex */
public abstract class PlayIntegrityUtils {
    public static String getTokenString() {
        String stringConfigValue = RemoteUtils.getStringConfigValue(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019317461417066022L), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019317555906346534L));
        if (stringConfigValue.equals(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019317671870463526L))) {
            Set stringSetConfigValue = RemoteUtils.getStringSetConfigValue(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019317701935234598L), PreferencesUtils$$ExternalSyntheticBackport1.m189m(new Object[]{Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019317796424515110L), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019317912388632102L), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019318002582945318L)}));
            return map((String) stringSetConfigValue.toArray()[Utilities.random.nextInt(stringSetConfigValue.size())]);
        }
        return map(stringConfigValue);
    }

    private static String map(String str) {
        switch (str.hashCode()) {
            case -1409683949:
                if (str.equals(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019318243101113894L))) {
                    return Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019318968950586918L) + LoginActivity.errorString(new IntegrityServiceException(RemoteUtils.getIntConfigValue(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019318848691502630L), -3).intValue(), null));
                }
                break;
            case -1385650691:
                if (str.equals(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019318402014903846L))) {
                    return Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019319282483199526L);
                }
                break;
            case -1190396462:
                if (str.equals(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019318492209217062L))) {
                    return null;
                }
                break;
            case 551846637:
                if (str.equals(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019318148611833382L))) {
                    return Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019318711252549158L) + LoginActivity.errorString(new IntegrityServiceException(-13, new IllegalArgumentException(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019318659712941606L))));
                }
                break;
            case 1656306827:
                if (str.equals(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019318311820590630L))) {
                    return Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019319106389540390L);
                }
                break;
            case 1699061288:
                if (str.equals(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019318032647716390L))) {
                    return Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019318522273988134L) + LoginActivity.errorString(new IntegrityServiceException(-2, null));
                }
                break;
        }
        return Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019319437102022182L);
    }
}
