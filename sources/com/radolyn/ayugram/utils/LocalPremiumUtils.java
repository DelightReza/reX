package com.radolyn.ayugram.utils;

import android.content.SharedPreferences;
import android.util.Base64;
import com.exteragram.messenger.backup.PreferencesUtils;
import org.lsposed.lsparanoid.Deobfuscator$AyuGram4A$TMessagesProj;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes4.dex */
public abstract class LocalPremiumUtils {
    private static boolean configLoaded;
    private static SharedPreferences.Editor editor;
    private static SharedPreferences preferences;
    private static final Object sync = new Object();

    static {
        loadConfig();
    }

    public static void loadConfig() {
        synchronized (sync) {
            try {
                if (configLoaded) {
                    return;
                }
                SharedPreferences preferences2 = PreferencesUtils.getPreferences(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019315395537796646L));
                preferences = preferences2;
                editor = preferences2.edit();
                configLoaded = true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void setFakePremiumEmojiStatus(long j, TLRPC.EmojiStatus emojiStatus) {
        NativeByteBuffer nativeByteBuffer;
        if (emojiStatus == null) {
            editor.remove(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019315477142175270L) + j).apply();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.utils.LocalPremiumUtils$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LocalPremiumUtils.$r8$lambda$708j3LXH53nTvalClpR0WzRrVvY();
                }
            });
            return;
        }
        NativeByteBuffer nativeByteBuffer2 = null;
        try {
            nativeByteBuffer = new NativeByteBuffer(emojiStatus.getObjectSize());
        } catch (Throwable unused) {
        }
        try {
            emojiStatus.serializeToStream(nativeByteBuffer);
            nativeByteBuffer.rewind();
            byte[] bArr = new byte[nativeByteBuffer.remaining()];
            nativeByteBuffer.readBytes(bArr, false);
            String strEncodeToString = Base64.encodeToString(bArr, 0);
            editor.putString(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019315532976750118L) + j, strEncodeToString).apply();
            nativeByteBuffer.reuse();
        } catch (Throwable unused2) {
            nativeByteBuffer2 = nativeByteBuffer;
            if (nativeByteBuffer2 != null) {
                nativeByteBuffer2.reuse();
            }
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.utils.LocalPremiumUtils$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    LocalPremiumUtils.$r8$lambda$gd5KPBC750FAhTlJMKaXCuhZaKk();
                }
            });
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.utils.LocalPremiumUtils$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                LocalPremiumUtils.$r8$lambda$gd5KPBC750FAhTlJMKaXCuhZaKk();
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$708j3LXH53nTvalClpR0WzRrVvY() {
        NotificationCenter.getInstance(UserConfig.selectedAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.mainUserInfoChanged, new Object[0]);
        NotificationCenter.getInstance(UserConfig.selectedAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.currentUserPremiumStatusChanged, new Object[0]);
    }

    public static /* synthetic */ void $r8$lambda$gd5KPBC750FAhTlJMKaXCuhZaKk() {
        NotificationCenter.getInstance(UserConfig.selectedAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.mainUserInfoChanged, new Object[0]);
        NotificationCenter.getInstance(UserConfig.selectedAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.currentUserPremiumStatusChanged, new Object[0]);
    }

    public static TLRPC.EmojiStatus getFakePremiumEmojiStatus(long j) {
        NativeByteBuffer nativeByteBuffer;
        byte[] bArrDecode;
        String string = preferences.getString(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019315588811324966L) + j, null);
        if (string == null) {
            return null;
        }
        try {
            bArrDecode = Base64.decode(string, 0);
            nativeByteBuffer = new NativeByteBuffer(bArrDecode.length);
        } catch (Throwable unused) {
            nativeByteBuffer = null;
        }
        try {
            nativeByteBuffer.writeBytes(bArrDecode);
            nativeByteBuffer.position(0);
            TLRPC.EmojiStatus emojiStatusTLdeserialize = TLRPC.EmojiStatus.TLdeserialize(nativeByteBuffer, nativeByteBuffer.readInt32(false), false);
            nativeByteBuffer.reuse();
            return emojiStatusTLdeserialize;
        } catch (Throwable unused2) {
            if (nativeByteBuffer != null) {
                nativeByteBuffer.reuse();
            }
            return null;
        }
    }

    public static void setLocalColorData(int i, int i2, long j, int i3, long j2) {
        editor.putInt(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019315644645899814L) + UserConfig.getInstance(i).getClientUserId(), i2).apply();
        editor.putLong(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019315717660343846L) + UserConfig.getInstance(i).getClientUserId(), j).apply();
        editor.putInt(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019315790674787878L) + UserConfig.getInstance(i).getClientUserId(), i3).apply();
        editor.putLong(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019315872279166502L) + UserConfig.getInstance(i).getClientUserId(), j2).apply();
    }

    public static int getLocalQuoteColor(long j) {
        return preferences.getInt(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019315953883545126L) + j, -1);
    }

    public static long getLocalQuoteEmoji(long j) {
        return preferences.getLong(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019316026897989158L) + j, -1L);
    }

    public static int getLocalProfileColor(long j) {
        return preferences.getInt(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019316099912433190L) + j, -1);
    }

    public static long getLocalProfileEmoji(long j) {
        return preferences.getLong(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019316181516811814L) + j, -1L);
    }
}
