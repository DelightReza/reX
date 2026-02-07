package com.radolyn.ayugram.utils.fcm;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.lsposed.lsparanoid.Deobfuscator$AyuGram4A$TMessagesProj;

/* loaded from: classes4.dex */
public class IntegrityServiceException extends ApiException {

    /* renamed from: a */
    private final Throwable f402a;

    @Override // java.lang.Throwable
    public final synchronized Throwable getCause() {
        return this.f402a;
    }

    public IntegrityServiceException(int i, Throwable th) {
        super(new Status(i, String.format(Locale.ROOT, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019319591720844838L), Integer.valueOf(i), Errors.m472a(i))));
        if (i == 0) {
            throw new IllegalArgumentException(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019319720569863718L));
        }
        this.f402a = th;
    }

    private static final class Errors {

        /* renamed from: a */
        private static final Map f403a;

        /* renamed from: b */
        private static final Map f404b;

        static {
            HashMap map = new HashMap();
            f403a = map;
            HashMap map2 = new HashMap();
            f404b = map2;
            map.put(-1, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019325759293881894L));
            map.put(-2, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019326742841392678L));
            map.put(-3, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019327357021716006L));
            map.put(-4, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019327752158707238L));
            map.put(-5, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019328791540792870L));
            map.put(-6, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019329212447587878L));
            map.put(-7, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019329689188957734L));
            map.put(-8, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019330260419608102L));
            map.put(-9, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019330754340847142L));
            map.put(-10, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019331467305418278L));
            map.put(-11, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019332120140447270L));
            map.put(-12, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019332622651620902L));
            map.put(-13, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019333133752729126L));
            map.put(-14, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019333528889720358L));
            map.put(-15, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019333885372005926L));
            map.put(-16, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019334233264356902L));
            map.put(-100, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019335087962848806L));
            map.put(-17, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019335624833760806L));
            map2.put(-1, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019335994200948262L));
            map2.put(-3, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019336071510359590L));
            map2.put(-2, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019336131639901734L));
            map2.put(-4, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019336221834214950L));
            map2.put(-14, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019336346388266534L));
            map2.put(-5, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019336466647350822L));
            map2.put(-6, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019336543956762150L));
            map2.put(-15, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019336647035977254L));
            map2.put(-7, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019336780179963430L));
            map2.put(-8, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019336853194407462L));
            map2.put(-9, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019336930503818790L));
            map2.put(-10, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019337029288066598L));
            map2.put(-11, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019337098007543334L));
            map2.put(-13, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019337162432052774L));
            map2.put(-16, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019337248331398694L));
            map2.put(-12, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019337385770352166L));
            map2.put(-100, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019337497439501862L));
            map2.put(-17, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019337561864011302L));
        }

        /* renamed from: a */
        public static String m472a(int i) {
            Map map = f403a;
            Integer numValueOf = Integer.valueOf(i);
            if (map.containsKey(numValueOf)) {
                Map map2 = f404b;
                if (map2.containsKey(numValueOf)) {
                    return ((String) map.get(numValueOf)) + Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019325170883362342L) + ((String) map2.get(numValueOf)) + Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019325746408980006L);
                }
            }
            return Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019325754998914598L);
        }
    }
}
